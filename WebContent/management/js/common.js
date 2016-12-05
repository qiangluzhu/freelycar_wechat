;
var common_ops = {
    getUrlPrefix:function(){
        return "/web";
    },
    buildDynamicUrl:function(path,params){
        var uri = window.location.href;
        var url =  "/web" +  path;
        if(uri.indexOf('/club')>0){
            url =  "/club" +  path;
        }
        var _paramUrl = '';
        if( params ){
            _paramUrl = Object.keys(params).map(function(k) {
                return [encodeURIComponent(k), encodeURIComponent(params[k])].join("=");
            }).join('&');
            _paramUrl = "?"+_paramUrl;
        }
        return url+_paramUrl
    },
    buildWebUrl:function( path ,params){
        var url =  "/web" +  path;
        var _paramUrl = '';
        if( params ){
            _paramUrl = Object.keys(params).map(function(k) {
                return [encodeURIComponent(k), encodeURIComponent(params[k])].join("=");
            }).join('&');
            _paramUrl = "?"+_paramUrl;
        }
        return url+_paramUrl

    },
    buildPicStaticUrl:function(bucket,img_key,params){
        bucket = bucket?bucket:"pic3";
        var url = "http://"+bucket+".s.rrr.me/"+img_key;

        var width = params.hasOwnProperty("w")?params['w']:0;
        var height = params.hasOwnProperty("h")?params['h']:0;
        if( !width && !height ){
            return url;
        }

        if( params.hasOwnProperty('view_mode') ){
            url += "?imageView2/"+params['view_mode'];
        }else{
            url += "?imageView2/1";
        }

        if( width ){
            url += "/w/"+width;
        }

        if( height ){
            url += "/h/"+height;
        }
        url += "/interlace/1";
        return url;
    },
    getTradeNounsCoach:function(){
        return $("#trade_nouns_coach").val();
    },
    UA_IS_PC:function(){
        return parseInt( $("#ua_is_pc").val() ) >= 1?true:false;
    },
    eventBind:function(){
        this.interval_id = setInterval("common_ops.noticeOps()",10000);
        //文本框失去焦点时隐藏tip提示层
        $('div').on('blur','input,textarea,select,textarea,password',function(){
            $(this).hideTip();
        });

    },
    leftNav:function() {
      $(".firstNav").click(function(){
				var arrow = $(this).find("i.arrow");
				if(arrow.hasClass("arrow_down")){
					arrow.removeClass("arrow_down").addClass("arrow_up");
          $(this).addClass('b1');
				}else if(arrow.hasClass("arrow_up")){
					arrow.removeClass("arrow_up").addClass("arrow_down");
				}
				$(this).parent().find("ul.subNav").slideToggle(250,function(){
          if($(this).is(':hidden')){
            $(this).prev(".firstNav").removeClass('b1');
          }
        });;
			});
    },
    renderTooltips:function(){
        $("a.icon-help, span.icon-help").mouseenter(function () {
            var $that = $(this);
            var ids= $that.data("dropdown").match(/\_(\d+)/);
            var id = ids[1];
            var $_divnum = $("#tips_" + id);
            if($_divnum.length != 1){
                var _div = "<div id=\'tips_" + id + "\'"
                    + "class=\'f-dropdown f-dropdown-help\' data-dropdown-content>"
                    + "<p class=\'text-center\'><i class=\'fa fa-spinner fa-spin tips_loading\'></i></p>"
                    + "</div>";
                $(_div).appendTo('body');
                var render = function (tooltip) {
                    var tmp = "<h6>" + tooltip.title + "</h6>"
                        + "<p>" + tooltip.content+ "</p>";
                    $("#tips_" + id).html(tmp);
                };
                $.ajax({
                    url:common_ops.getUrlPrefix() + "/tooltip/index?id=" + id,
                    type: 'GET',
                    dataType:'json',
                    success:function(res){
                        if(res.code != 200) {
                            return;
                        }
                        render(res.data);
                    }
                });
            }
        });
    },
    setCurrentNav:function(){

        if(!$(".leftNav")){
            return;
        }
        var pathname = window.location.pathname;
        var target = $(".leftNav");
        var nav_name = null;

        if( pathname == "/web" ){
            pathname = "/web/shop/index";
        }

        if(  pathname.indexOf("/web/album") > -1  ){
            nav_name = "shop";
            pathname = "/web/album/list";
        }

        if(  pathname.indexOf("/web/shop") > -1  ){
            nav_name = "shop";
        }

        if (pathname.indexOf("/web/plugins/member") > -1) {
            nav_name = "plugins_member";
        } else if(pathname.indexOf("/web/plugins") > -1) {
            nav_name = "plugins";
        }

        if( pathname.indexOf("/web/manage") > -1 || pathname.indexOf("/web/staff") > -1 ){
            nav_name = "role";
        }

        if( pathname.indexOf("/web/manage/member") > -1
            || pathname.indexOf("/web/card") > -1
            || pathname.indexOf("/web/product") > -1
            || pathname.indexOf("/web/member/stat") > -1
        ){
            nav_name = "member";
        }

        if( pathname.indexOf("/web/plugins/training") > -1 || pathname.indexOf("/web/market") > -1
            || pathname.indexOf("/web/plugins/group") > -1 || pathname.indexOf("/plugins/bargain") > -1 || pathname.indexOf("/plugins/channel") > -1){
            nav_name = "training";
        }


        if( pathname.indexOf("/web/order") > -1 || pathname.indexOf("/web/appoint") > -1){
            nav_name = "appoint";
        }

        if( pathname.indexOf("/web/course") > -1 || pathname.indexOf("/web/appoint/comments") > -1 ){
            nav_name = "course";
        }

        if( pathname.indexOf("/web/stat") > -1 ){
            nav_name = "stat";
        }

        if( pathname.indexOf("/web/finance") > -1 || pathname.indexOf("/web/user/bank") > -1 ){
            nav_name = "finance";
        }

        if( pathname.indexOf("/web/setting") > -1  || pathname.indexOf("/web/role") > -1 || pathname.indexOf("/user/draw") > -1 ){
            nav_name = "setting";
        }

        if( pathname.indexOf("/web/finance/plugin") > -1
            || pathname.indexOf("/web/finance/invoice") > -1
            || pathname.indexOf("/web/setting/sms") > -1
            || pathname.indexOf("/web/shop/zhaopin") > -1  ){
            nav_name = "plugins";
        }

        if( pathname.indexOf("/dian") > -1 ){
            nav_name = "dian";
        } 

        if( nav_name == null ){
            return;
        }


        target = $( target.find("#nav_" + nav_name) );
        if( target.find(".subNav").size() < 1 ){
            $( target.find("a.firstNav").get(0)).addClass("current");
            return;
        }

        var nav_target = $( target.find("div.firstNav").get(0) );
        var subnav_target = $( target.find("ul.subNav").get(0) );
        $(nav_target.find("i").get(1)).removeClass("arrow_down");
        $(nav_target.find("i").get(1)).addClass("arrow_up");
        subnav_target.css("display","block");
        nav_target.addClass("b1");

        var getParams = this.getRequest();
        if( pathname == "/web/course/calendar" ){
            if( getParams['from'] == "team" ){
                $($(".leftNav .nav_course .subNav a").get(0)).addClass("current");
            }else{
                $($(".leftNav .nav_course .subNav a").get(1)).addClass("current");
            }
            return;
        }

        //几个特殊的特殊判断
        if(pathname.indexOf("/web/manage") >= 0 && getParams['from'] == "coach"){
            $($(".leftNav #nav_role .subNav a").get(1)).addClass("current");
            return;
        }
        if( getParams['from'] == "team" ){
            $($(".leftNav .nav_course .subNav a").get(0)).addClass("current");
            return;
        }
        if( getParams['from'] == "coach" ){
            $($(".leftNav .nav_course .subNav a").get(1)).addClass("current");
            return;
        }
        if( pathname == "/web/order/list" ){
            $($(".leftNav #nav_appoint .subNav a").get(1)).addClass("current");
            return;
        }
        if( pathname == "/web/finance/balance" ){
            $($(".leftNav #nav_finance .subNav a").get(1)).addClass("current");
            return;
        }
        if( pathname.indexOf("/web/shop") >= 0 ){
            $($(".leftNav #nav_shop .subNav a").get(0)).addClass("current");
            return;
        }
        if( pathname.indexOf("/dian/product/list") >= 0 ){
            $($(".leftNav #nav_dian .subNav a").get(0)).addClass("current");
            return;
        }
        if( pathname.indexOf("/dian/order/list") >= 0 ){
            $($(".leftNav #nav_dian .subNav a").get(1)).addClass("current");
            return;
        }
        if (pathname == "/web/dian") {
            return;
        }
        if (nav_name == 'plugins_member') {
            $($(".leftNav #nav_plugins_member .subNav a").get(0)).addClass("current");
            return
        }

        var breadcrumbs_href = $(".page_breadcrumbs a").eq(1).attr("href");
        $(".leftNav li a").each(function(){
            var href = $(this).attr("href");
            if(pathname == href || href.indexOf(pathname) >= 0){
                $(this).addClass("current");
                return;
            }

            //其余用第二级面包屑来判断
            if(breadcrumbs_href){
                if(breadcrumbs_href == href || href.indexOf(breadcrumbs_href.split('?')[0]) >= 0){
                    $(this).addClass("current");
                    return;
                }
            }

        });
    },
    getRequest:function() {
        var url = location.search; //获取url中"?"符后的字串
        var theRequest = new Object();
        if (url.indexOf("?") != -1) {
            var str = url.substr(1);
            strs = str.split("&");
            for(var i = 0; i < strs.length; i ++) {
                theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
            }
        }
        return theRequest;
    },
    setMobileNav:function(){
      $(".show_left_nav").click(function() {
        $(".leftNav").toggle(0,function(){
          if($(this).is(':hidden')){
            $(".container").removeClass('fix-mobile').css('margin-left','0');
          }else{
            $(".container").addClass('fix-mobile').removeAttr('style');
          }
        });
      });
    },
    noticeOps:function(){
        if( $(".login_status").size() < 1 || parseInt( $(".login_status").val() ) != 1 ){
            clearInterval(common_ops.interval_id);
            return;
        }
        if( !this.UA_IS_PC() ){
            clearInterval(common_ops.interval_id);
            return;
        }

        $.ajax({
            url:common_ops.getUrlPrefix() + "/notice/index",
            type:'GET',
            dataType:'json',
            success:function(res){
                if( res.code == -302 ){//无权访问
                    clearInterval(common_ops.interval_id);
                    return;
                }
                if( res.code == 200 && res.data.id ){
                    $(".pop_window h3").html( '<i class="pop_icon icon_'+res.data.type+'"></i>' + res.data.title);
                    $(".pop_window a.content").html( res.data.content );
                    $(".pop_window #notice_id").val( res.data.id);
                    $(".pop_window #notice_id").attr("data-type", "notice");
                    $(".pop_window .link").attr("href",res.data.detail_url);

                    $(".pop_window li").removeClass('current');
                    $(".pop_window .notice_type_"+res.data.type).addClass('current');
                    $(".pop_window .pop_icon").addClass('icon_'+res.data.type);
                    if(res.data.stat['pay_count'] > 0){
                        $(".pop_window [name=stat_1]").addClass('msg_num');
                        $(".pop_window [name=stat_1]").text(res.data.stat['pay_count']);
                    }
                    if(res.data.stat['sys_count'] > 0){
                        $(".pop_window [name=stat_2]").addClass('msg_num');
                        $(".pop_window [name=stat_2]").text(res.data.stat['sys_count']);
                    }
                    if(res.data.stat['order_count'] > 0){
                        $(".pop_window [name=stat_3]").addClass('msg_num');
                        $(".pop_window [name=stat_3]").text(res.data.stat['order_count']);
                    }
                    if(res.data.stat['checkin_count'] > 0){
                        $(".pop_window [name=stat_4]").addClass('msg_num');
                        $(".pop_window [name=stat_4]").text(res.data.stat['checkin_count']);
                    }
                    if(res.data.stat['cancel_count'] > 0){
                        $(".pop_window [name=stat_5]").addClass('msg_num');
                        $(".pop_window [name=stat_5]").text(res.data.stat['cancel_count']);
                    }


                    $(".pop_window a.content").unbind().click(function(){
                        $(".pop_window .close").click();
                    });

                    $(".pop_window .close").unbind().click(function(){
                        var id = $(".pop_window #notice_id").val();
                        var type  = $(".pop_window #notice_id").attr("data-type");
                        if( parseInt(id) < 1 ){
                            return;
                        }
                        if( type != "notice" ){
                            $(".pop_window").hide();
                            return;
                        }
                        $.ajax({
                            url:common_ops.getUrlPrefix() + "/notice/check",
                            type:'POST',
                            data:{'id':id},
                            dataType:'json',
                            success:function(res){
                                $(".pop_window").hide();
                            }
                        });
                    });

                    $(".pop_window").show();
                }
            }
        });


    },
    loginBBS:function(data){
        $.ajax({
            url:common_ops.getUrlPrefix()+'/user/ucenter',
            type:'GET',
            data: data,
            dataType: 'json',
            success:function(res){
                if (res.code == 200) {
                    $("body").append("<div style='display:none;'>"+res.msg+"</div>");
                }
            }
        });
    },

    noticeBanner:function(){
        if( $(".login_status").size() < 1 || parseInt( $(".login_status").val() ) != 1 ){
            return;
        }
        if( !$("#ua_is_pc").val() ){
            return;
        }
        $.ajax({
            url:this.getUrlPrefix() + "/notice/scroll",
            type:'GET',
            dataType:'json',
            success:function(res){
                if( res.code != 200 ){
                    return;
                }
                var data = eval( res.data );
                if( data.length <= 0  ){
                    return;
                }
                var li_html = "";
                for(var i=0;i < data.length;i++){
                    li_html = '<li style="height: 30px;"><a target="_blank" href="'+ data[i].url +'">'+ data[i].title +'</a></li>';
                    $("#notice_banner_wrap .line").append( li_html );
                }
                var _wrap= $('ul.line');//定义滚动区域
                var _interval = 3000;//定义滚动间隙时间
                var _moving;//需要清除的动画
                _wrap.hover(function(){
                    clearInterval(_moving);//当鼠标在滚动区域中时,停止滚动
                },function(){
                    _moving=setInterval(function(){
                        var _field=_wrap.find('li:first');//此变量不可放置于函数起始处,li:first取值是变化的
                        var _h=_field.height();//取得每次滚动高度(多行滚动情况下,此变量不可置于开始处,否则会有间隔时长延时)
                        _field.stop().animate({marginTop:-_h+'px'},600,function(){//通过取负margin值,隐藏第一行
                            _field.css('marginTop',0).appendTo(_wrap);//隐藏后,将该行的margin值置零,并插入到最后,实现无缝滚动
                        })
                    },_interval);//滚动间隔时间取决于_interval
                }).trigger('mouseleave');//函数载入时,模拟执行mouseleave,即自动滚动
            }
        });


    },
    //引导信息弹出层
    guideLayer:function(){
      var that = this;
      var num=$('.guide_img').find('img').length;
      var i=0;
      refresh_guide_info();
      $('.img_wrap').css({'width':700*num+'px'});
      $('.guide_status li').eq(i).addClass('current');
      function refresh_guide_info(){
        $('.guide_layer .text p').eq(i).show().siblings('p').hide();
        $('.guide_status li').removeClass('current').eq(i).addClass('current');
        $('.guide_img .img_wrap').stop().animate({
          'left':-i*700+'px'
        },600)
      }
      //点击下一步切换
      $('.guide_layer .button.next').click(function(){
          i++;
          if(i==num){
            i=num-1;
          }
          refresh_guide_info();
      });
      $('.guide_status li').click(function(){
        i=$('.guide_status li').index(this);
        $(this).addClass('current').siblings('li').removeClass('current');
        refresh_guide_info();
      });
    },
    func_noticeDisplay:function(){
        if( $(".login_status").size() < 1 || parseInt( $(".login_status").val() ) != 1 ){
            return;
        }
        var that = this;
        $.ajax({
            url:that.getUrlPrefix() + "/notice/func_index",
            type:'GET',
            dataType:'json',
            success:function(res){
                if( !$.isEmptyObject(res.data) ){
                    $('.guide_layer').show();
                    $(".guide_layer").append(res.data);
                    that.guideLayer();
                    that.func_noticeFinished();
                }
            }
        });
    },
    func_noticeFinished:function(){
        $('.guide_layer .finished').unbind().click(function(){
            $.ajax({
                url:common_ops.getUrlPrefix()+'/notice/func_ops',
                type:'POST',
                data: {'func_notice_id':$(".guide_layer [name=func_notice_id]").val()},
                dataType: 'json',
                success:function(res){
                    if (res.code == 200) {
                        $('.guide_layer').hide();
                    }
                }
            });
        });
    },
    //一键上架优质商品浮层
    postGuideLayer: function(){
        if ($('.post_guide_layer').length == 0) {
            return;
        }
        var date = new Date();
        if ($.cookie('last_show_mall_guide_date') == date.toLocaleDateString()) {
            return;
        }
        $('.post_guide_layer').show();
        $('.post_guide_layer .close').click(function(){
            $.cookie('last_show_mall_guide_date', date.toLocaleDateString(), {expires: 1});
            $('.post_guide_layer').hide();
        });
        $('.post_guide_layer .try').click(function(){
            $.post('/web/default/add_quality_products', {}, function (response) {
                location.href = '/web/dian/product/list';
            }, 'json');
            $.cookie('last_show_mall_guide_date', date.toLocaleDateString(), {expires: 1});
            $('.post_guide_layer').hide();
        });
    },
    popLayer:function( url,params ){
        var data = {};
        if( params.hasOwnProperty('data') ){
            data = params['data'];
        }

        var target_handle = params['target'];
        $.ajax({
            url:common_ops.buildWebUrl(url),
            type:'POST',
            data:data,
            dataType:'json',
            success:function( res ){
                if( res.code == 200 ){
                    target_handle.html( res.data.html );
                    target_handle.foundation('reveal', 'open');
                }else{
                    $.alert( res.msg );
                }
            }
        });
    },
    resizeContent:function(){
      var windowHeight = $(window).height(),
          mainContentHeight = $(".mainContent").height(),
          navLeftContentHeight = $(".navInner").height();
      var navLeftMinHeight = windowHeight - $(".sys_avatar").outerHeight(true) - $(".sys_logo").outerHeight(true),
          mainContentMinHeight = windowHeight - $(".topNav").height() - $(".breadCrumb").outerHeight(true) - $(".pageStatus").outerHeight(true) - $(".pageTab").outerHeight(true) - $(".footNav").height();
      if(mainContentHeight < mainContentMinHeight){
        $(".mainContent").css('min-height', mainContentMinHeight);
      }
      if(navLeftContentHeight < navLeftMinHeight){
        $(".navInner").css('min-height', navLeftMinHeight);
      }
      // 解决页面加载时 页脚闪动问题
      $(".footNav").show();
    },
    renderPlusButton: function(){
      var rightConner = $(".breadCrumb").find(".rightConner");
      if(rightConner.length >= 1){
        var plusButton = rightConner.find('.fa-plus')
        plusButton.each(function(index) {
          $(this).parent().addClass('green')
        });

      }
    },
    tableActionBtns:function(){
      if($("[data-toggle='tooltip']") && $("[data-toggle='tooltip']").length){
        $('[data-toggle="tooltip"]').tooltip({
          trigger : 'hover'
        });
      }
    }
};
$(document).ready(function(){
    // common_ops.noticeBanner();
    common_ops.resizeContent();
    common_ops.renderPlusButton();
    common_ops.setCurrentNav();
    common_ops.setMobileNav();
    common_ops.eventBind();
    common_ops.leftNav();
    common_ops.renderTooltips();
    common_ops.func_noticeDisplay();
    common_ops.postGuideLayer();
    common_ops.tableActionBtns();

    if ($(".login_bbs") && $(".login_bbs").length) {
        common_ops.loginBBS({type: $(".login_bbs").val()});
    }
    $(window).resize(function(){
      common_ops.resizeContent();
    });
    window.onerror = function(message, url, lineNumber,columnNo,error) {
        var data = {
            'message':message,
            'url':url,
            'error': ( error != undefined &&  error.hasOwnProperty("stack") )?error.stack:error
        };
        $.ajax({
            url:"/error/capture",
            type:'post',
            data:data,
            success:function(){

            }
        });
        return true;
    };
});

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function(fmt)
{ //author: meizz
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
};
