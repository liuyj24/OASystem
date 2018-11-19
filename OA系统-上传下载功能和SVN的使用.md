### 文件上传下载展示和思路
1. 不使用OA系统, 使用两个独立的项目进行练习
2. 使用上传下载功能直接使用太复杂, 使用两个jar包
- commons-fileupload.jar    主要是使用这个jar包
- commons-io.jar    上面的jar包离不开这个jar包
3. 上传操作3个基本的类
- FileItemFactory DiskFileItemFactory:工厂类, 生产FileItem
- FileItem:每个表单项都会被封装成一个FileItem对象
- ServletFileUpload:实现上传操作,将表单数据封装到FileItem中

### 开始上传并理解上传API的作用
1. 导入两个jar包
2. 视图层的基本操作 : 修改表单的默认属性, 上传的标签类型是file
```
<form action='...' method="post" enctype="mutipart/form-data">
    <input type='file' name="photo">
</form>
```
3. servlet控制层, 开始时的固定套路3步, 第4步视具体操作而定
```
//1. 创建FileItemFactory对象
FileItemFactory = new DiskFileItemFactory();//FileItemFactory是一个接口
//2. 创建ServletFileUpload对象
ServletFileUpload upload = new ServletFileUpload(factory);
//3. 通过ServletFileUpload对象实现上传操作, 将客户端一个个表单项封装到一个个FileItem中
List<FileItem> itemList = null;
try{
    itemList = upload.parseRequest(request);
}catch(FileUploadException e){
    e.printStackTrace();
}

//通过打印以下内容, 学习下面的属性, **需要掌握这几个属性的用法**
    item.isFormField()      //是不是file表单项,是file(false), 不是file(true)
    item.getFieldName()     //表单项name属性的值
    item.getString()        //对于非file表单项, value属性的值;对于file表单项, 是文件内容
    item.getName()          //对于file表单项, 上传文件的名称;对于非file表单项, 返回null
    item.getContentType()   //对于file表单项,是上传文件mime类型, 对于非file表单项, 返回null
    item.getSize()          //对于file表单项, 上传文件的大小;对于非file表单项, 是value值的宽度

//乱码解决:
//1.解决非表单项的中文乱码问题, 比如表单项中的name属性
item.getString("utf-8");
//2.解决file表单项的文件名中文乱码问题
ServletFileUpload upload = new ServletFileUpload(factory);
upload.setHeaderEncoding("utf-8");

```
### 上传文件到指定目录
```
//遍历各个FileItem(相当于对各个表单项进行处理)
String name = null;
int age = 0;
double score = 0;
for(int i = 0; i < itemList.size(); i++){
    //取出第i个表单项
    FileItem item = itemList.get(i);
    
    //对各个表单项进行处理(普通表单项和文件表单项要分开处理)
    if(item.isFormField()){//判断是不是普通表单项
        String fileName = item.getFileName();
        //name
        if("name".equals(fileName){
            name = item.getString("utf-8");
        }
        //age
        if("age".equals(fileName){
            age = Integer.parseInt(item.getString());
        }
        //score
        if("score".equals(fileName)){
            score = Double.parseDouble(item.getString());
        }
        
    }else{//文件表单项
        //photo
        if("photo".equals(fileName{
            //指定上传的文件夹
            File dir = new File("d:/upload");//Servlet是服务器技术, 此处填服务器端的路径
            //如果文件夹不存在就创建
            if(!dir.exists()){
                dir.mkdirs();
            }
            //指定上传的文件名
            String photoName = item.getName();
            //指定上传的文件和文件名
            File file = new File(dir, photoName);
            //上传该照片到指定位置
            try{
                item.write(file);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        //other
    }
}

```
1. 为了后面对上传的文件方便访问, 到tomcat->webapps目录下找到当前项目的文件夹, 创建文件夹upload
2. 把Servlet中指定文件夹的内容改掉, 文件夹路径改为upload的绝对路径, 但是还是存在不足, 假如下次Tomcat的路径改到了别的盘, 原Tomcat的的路径访问不到了


### 完善上传文件到指定目录
大致思路:
- 完善1:限制上传文件的大小, 使用upload.setSizeMax(1024);
- 完善2:限制上传文件的类型
- 完善3:为了避免同名文件的覆盖,文件重命名
```
    }else{//文件表单项
        //photo
        if("photo".equals(fileName{
            //完善5:限制上传文件大小16kb(但是这样限制大小是错误的, 相当于文件已经上传到服务器了, 只是不把它添加到upload文件中罢了)
            /*
            long size = item.getSize();
            if(size > 16*1024){
                //返回错误信息
                return;
            }
            */
            //应该在创建ServletFileUpload对象的时候设置文件大小的上限, 具体代码补充在本段末尾
            
            //完善4:只上传jpg, jpeg和gif文件, 文件类型的具体名称到Tomcat文件夹中的conf->web.xml中查看
            String contentType = item.getContentType();//images/jpg
            if(!"image/jpeg".equals(contentType) && !"image/gif".equals(contentType)){
                //回显错误信息...
                return;
            }
            
            
            //指定上传的文件夹
            //完善3:直接使用物理路径不灵活, 动态获取当前项目路径再加上逻辑路径"/upload"更好
            String realPath = this.getServletContext().getRealPath("/upload");
            File dir = new File(realPath);//Servlet是服务器技术, 此处填服务器端的路径
            //如果文件夹不存在就创建
            if(!dir.exists()){
                dir.mkdirs();
            }
            //拿到上传的文件名
            String photoName = item.getName();
            //指定上传的文件和文件名
            //完善2:为了防止文件的同名覆盖,上传到服务器的文件重新命名
            UUID uuid = UUID.randomUUID();//获得一个不重复的uuid作为文件名
            String extName = photoName.subString (photoName.lastIndexOf("."));//拿到上传图片的扩展名(类型)
            String fileName = uuid.toString()+extName;
            File file = new File(dir, fileName);
            //上传该照片到指定位置
            try{
                item.write(file);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        //other
    }
    
    
    //完善5:限制上传的单个文件和所有文件的大小, 建议使用此种方式
    //2.创建ServletFileUpload对象
    ServletFileUpload upload = new ServletFileUpload(factory);
    upload.setHeaderEncoding("utf-8");
    upload.setFileSizeMax(16*1024);//单个文件的上限
    upload.setSizeMax(5*16*1024);//一次上传的所有文件的总大小的上限
    //在对上传文件代码try-catch的时候要回显文件超过大小的错误信息, 并且return

```

### 保存上传信息到数据库
1. 数据库中保存的照片需要有两个名称字段, realName和photoName
```
//在servlet层中封装好javabean的信息
realName = item.getName();
photoName = uuid.toString()+extName;
...
```
2. 经过service层和dao层, 常规操作保存到数据库中
3. 查找信息后, 如何在视图层显示照片---图片标签中src属性直接访问服务器中的upload文件下的图片
```
<img alt="" src="upload/${stu.photoName}" width="100px">
```

### 将服务器的图片在客户端下载
```
//视图层的代码:
<a href="servlet/DownServlet?id=${stu.id}">下载</a>
//Servlet层中的代码:
//1.获取学生的编号
int id = Integer.parseInt(request.getParameter("id"));
//2.调用业务层获取学生的全部信息
Student stu = stuService.findById(id);
//3.通过IO流实现照片下载(从服务端到客户端)
//3.1创建一个输入流和输出流
String realPath = this.getServletContext().getRealPath("/upload");
String fileName = realPath + "/" +stu.getPhotoName();
File file = new File(fileName);
InputStream is = new FileInputStream(file);//读服务器端的一个文件
OutputStream os = response.getOutputStream();//写到客户端
//3.2使用输入流和输出流完成复制操作(服务器---客户端)
IOUtils.copy(is,os);
//3.3
is.close();
os.close();
```

### 完善下载操作
```
//视图层的代码:
<a href="servlet/DownServlet?id=${stu.id}">下载</a>
//Servlet层中的代码:
//1.获取学生的编号
int id = Integer.parseInt(request.getParameter("id"));
//2.调用业务层获取学生的全部信息
Student stu = stuService.findById(id);
//3.通过IO流实现照片下载(从服务端到客户端)
//3.1创建一个输入流和输出流
String realPath = this.getServletContext().getRealPath("/upload");
String fileName = realPath + "/" +stu.getPhotoName();
File file = new File(fileName);


response.setContentLength((int)file.length());//文件长度
response.setContentType(stu.getPhotoType());//MIME类型
//解决下载时文件名乱码问题
String realName = stu.getRealName();//中文.jpg
String userAgent = request.getHeader("User-Agent").toLowerCase();//获得请求中和浏览器有关的信息
if(userAgent.indexOf("mise") > 0){//判断是不是ie浏览器
    realName = URLEncoder.encode(realName, "utf-8");
}else{//不是ie浏览器的话只要按常规思路, 把服务器上utf-8的内容转化为"iso-8859-1"发送到浏览器即可
    realName = new String(realName.getBytes("utf-8"), "iso-8859-1");
}
response.setHeader("Content-disposition", "attachment;filename="+stu.getRealName());


InputStream is = new FileInputStream(file);//读服务器端的一个文件
OutputStream os = response.getOutputStream();//写到客户端
//3.2使用输入流和输出流完成复制操作(服务器---客户端)
IOUtils.copy(is,os);
//3.3
is.close();
os.close();
```



### 安装和使用Visual SVN
1. 先安装服务端, 默认一直到选择路径, 其中Location是这个软件安装的位置, Repositories(仓库)是提交的代码存放的地方, 并默认使用svn的认证方式
2. 创建一个新的版本库, 对Repositories单击右键, 新创建版本库:MyRepository, 选择Create dafault structure(trunk, branches, tags)选择默认结构:主干,分支,标签
3. 开发的最新代码放在trunk, 有意义的历史版本保存在tags中, 从主干中拉分支并在完成分支后合并到主干中是一个高级功能
4. 在user中可以创建开发人员和测试人员
5. 使用groups功能把开发人员和测试人员分组
6. 在讲访问权限之前, 先介绍如何使用获取仓库的内容, 对仓库单击右键Copy Url, 然后到浏览器打开, 输入用户名密码即可访问
7. 对仓库单击右键选择properties, 可以管理用户(组群)的权限
8. [idea安装使用svn](https://blog.csdn.net/qq_27093465/article/details/74898489)