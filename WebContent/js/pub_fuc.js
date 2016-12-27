	/**
 * 
 */
/*$.ajaxSetup({ cache: false });*/

//setCookie
var setCookie = function (name,value)
{
    var exp = new Date();
    exp.setTime(exp.getTime() + 24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}
//getCookie
var getCookie = function (name)
{
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}
//deleteCookie 注意：参数name为key ，不是value
var deleteCookie = function ( name ) {
	  document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}


/**
 * Tools
 */
var isEmpty =  function(str){
	return str=="" || str==null || str=="null" || str==undefined;
}


var isNotEmpty =  function(str){
	return str!="" && str!=null && str!="null" && str!=undefined;
}


/**
 * native ajax
 */
function nativeAjax(type,url,ajaxdone,param){
	param = (typeof param === 'undefined') ? '' : param;
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onload =ajaxdone;
	xmlhttp.open(type,url,true);
	if(type=='get' || type=='GET'){
		xmlhttp.send();
		
	} else if(type=='post' || type=='POST'){
		xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlhttp.send(param);
	}
	
}

/**
 * param progressEvent
 */
var getResult = function(e){
	return e.currentTarget.responseText;
}


/**
 * parser jsonstr to jsonobject
 */
var jsonParse = function(mystr){
	var res=eval('('+mystr+')');
	return res;
}


/**
 * 用户登陆权限
 * 
 */
$(function(){
	 var url = window.location.href;
	 var index = url.indexOf("login");
	 var index2 = url.indexOf("management");
	 if(index2>=0 && index<0){
		var login_sign = getCookie("login_sign");
		if(isEmpty(login_sign)){
			 window.location.href = "login.html";
		 }else{
			 
			/* $("#hover").find("span.glyphicon-user").text(login_sign);*/
			 $("#hover").find("li").click(function(){
				 deleteCookie("login_sign");
				 window.location.href = "login.html";
			 });
		 }
		 
	 }
	 
});


/**
 * 
 * 格式化日期
 * 
 */
Date.prototype.yyyymmdd = function() {   
    var yyyy = this.getFullYear().toString();                                    
    var mm = (this.getMonth()+1).toString(); // getMonth() is zero-based         
    var dd  = this.getDate().toString();             
    
    var HH = this.getHours().toString();       //获取当前小时数(0-23)
    var MM = this.getMinutes().toString();     //获取当前分钟数(0-59)
    var ss = this.getSeconds().toString();      
    return yyyy + '-' + (mm[1]?mm:"0"+mm[0]) + '-' + (dd[1]?dd:"0"+dd[0])+ ' ' +(HH[1]?HH:"0"+HH[0])+ ':' +(MM[1]?MM:"0"+MM[0])+ ':' +(ss[1]?ss:"0"+ss[0]);
};  



/**
 * 
 */

var curpage =1;
var  temp = "";
var pagebutton = function(pageNums){
	$("#mypager").on("click","li",function(e){
		//再去查一共多少页
		var $target = $(e.currentTarget);
		
		if($target.hasClass("sy")){
			curpage = 1;
		}else if($target.hasClass("syy")){
			curpage = --curpage>1?curpage:1;
		}else if($target.hasClass("xyy")){
			curpage = ++curpage>pageNums?pageNums:curpage;
		}else if($target.hasClass("wy")){
			curpage=pageNums;
		}
		
		if(curpage!=temp){
			console.log(curpage);
 			loadData(curpage);
 		}
		temp = curpage;
			
});
	
}



/**
 * 解析url中的参数
 * 
*/

var get_param = function (name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}


/**
 * 计算列表中的序号 base 1
 * @param start:第几页
 * @param number:每页的数量
 * @param index:在该页的index base 0
 * 
 */
var getPageIndex = function(start,number,index){
	return (start-1)*number+index+1;
}


