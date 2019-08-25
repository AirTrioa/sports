var BRS = BRS || {};
(function() {

  BRS.preLoading = function(type){
    var html = '<div id="preloading"><div class="spinner"><div class="rect1"></div><div class="rect2"></div><div class="rect3"></div><div class="rect4"></div><div class="rect5"></div></div></div>';
    if(type == 'show'){
      $("body").prepend(html);
    }else if(type == 'hide'){
      setTimeout(function () {
        $("#preloading").remove();
      }, 500);
    }
  };

  BRS.fileLoading = function (type) {
    if(type == 'show'){
      if($("#fileloading").length == 0){
        var html = '<div id="fileloading"><div class="spinner"><div class="spinner-icon"></div></div></div>';
        $("body").prepend(html);
      }
      NProgress.start();
    }else if(type == 'hide'){
      $("#fileloading").remove();
      NProgress.done();
    }
  };

  BRS.isBlank = function (str) {
    return (str == null || str == '');
  };
  BRS.isNotBlank = function (str) {
    return !this.isBlank(str);
  };
  BRS.isNullStr = function (str) {
    return "null"==str;
  };
  BRS.isUndefined= function (value) {
    return typeof(value)=="undefined";
  };

  BRS.openAppframeTab = function (fnPnId,fnName,fnUrl) {
    document.domain = APPFRAME_DOMAIN;
    try {
      window.parent.Appframe.tabLoadHTML({"fnName":fnName,"fnPnId":fnPnId,"fnUrl":fnUrl});
    }catch (e){
      console.error("Outercatch caught " + e);
      window.open(fnUrl);
    }
  };
  
  BRS.setWorkItemWindowSize = function (width,height) {
    document.domain = APPFRAME_DOMAIN;
    try {
      window.parent.Appframe.workflow.WorkList.setWidth(width,height);
    }catch (e){
      console.error("Outercatch caught " + e);
    }
  };
  
  BRS.getQueryString = function(name){
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r != null){
      return unescape(r[2]);
    }
    return null
  };

  BRS.setUrlParam = function(param, value){
    var query = window.location.search.substr(1);
    var p = new RegExp("(^|)" + param + "=([^&]*)(|$)");
    if(p.test(query)) {
      var firstParam = query.split(param)[0];
      var secondParam = query.split(param)[1];
      if(secondParam.indexOf("&") > -1) {
        var lastParam = secondParam.split("&")[1];
        return "?"+firstParam+"&"+param+"="+value+"&"+lastParam;
      } else {
        if(firstParam) {
          return "?"+firstParam+param+"="+value;
        } else {
          return  "?"+param+"="+value;
        }

      }
    } else {
      if(query == "") {
        return "?"+param+"="+value;
      } else {
        return "?"+query+"&"+param+"="+value;
      }
    }
  };

  BRS.removeMessage = function () {
    $("#isIEBox").remove()
  };
  BRS.isIE = function () {
    var userAgent = navigator.userAgent;
    var isIE11 = (/Trident\/7\./).test(userAgent);
    var isOpera = userAgent.indexOf('Opera') > -1;
    if((userAgent.indexOf('compatible') > -1 && userAgent.indexOf('MSIE') > -1 && !isOpera) || isIE11){
      var html = '<div id="isIEBox">请使用最新版本Chrome！<i class="fa fa-close" onclick="BRS.removeMessage()"></i></div>';
      $(".sidebar-mini").prepend(html);
    }
    var attr = userAgent.split(' ');
    var chromeVersion = '';
    for( var i=0; i<attr.length; i++){
      if(/chrome/i.test(attr[i])){
        chromeVersion = Number(attr[i].split('/')[1].split('.')[0])
      }
    }
    if(BRS.isNotBlank(chromeVersion) && chromeVersion < 49 ){
      var html = '<div id="isIEBox">您使用的谷歌浏览器版本过低，请升级到最新版本！<i class="fa fa-close" onclick="BRS.removeMessage()"></i></div>';
      $(".sidebar-mini").prepend(html);
    }
  };

  //删除字符串中的一串字符
  BRS.removeAttrInString = function (attr,string) {
    return string.replace(new RegExp(attr,'gm'),'')
  };
  // json对象数组根据对象属性排序(order: asc或者desc,sortBy:属性名)
  BRS.arraySortByArrayItem = function(order,sortBy){
        var oldAplah = (order === 'asc') ? '>' : '<';
        var arraySortFun = new Function('a', 'b', 'return a.' + sortBy + oldAplah + 'b.' + sortBy + '?1:-1');
        return arraySortFun;
  };
  // 将MB转化成GB
  BRS.changeMBToGB = function(limit){
    var size = "";
    size = (limit/(1024)).toFixed(2);
    var sizeStr = size + "";                             //转成字符串
    var index = sizeStr.indexOf(".");                    //获取小数点处的索引
    var dou = sizeStr.substr(index + 1 ,2);              //获取小数点后两位的值
    var last = sizeStr.substr(index + 1 ,1);            //获取小数点后一位的值
    if(dou == "00"){                                    //判断后两位是否为00，如果是则删除00
      return sizeStr.substring(0, index) + sizeStr.substr(index + 3, 2)
    }
    return size;
  };
  // 克隆
  BRS.clone = function (obj) {
    var o, i, j, k;
    if (typeof(obj) != "object" || obj === null) return obj;
    if (obj instanceof (Array)) {
      o = [];
      i = 0;
      j = obj.length;
      for (; i < j; i++) {
        if (typeof(obj[i]) == "object" && obj[i] != null) {
          o[i] = arguments.callee(obj[i]);
        } else {
          o[i] = obj[i];
        }
      }
    } else {
      o = {};
      for (i in obj) {
        if (typeof(obj[i]) == "object" && obj[i] != null) {
          o[i] = arguments.callee(obj[i]);
        } else {
          o[i] = obj[i];
        }
      }
    }
    return o;
  }
  
})();