/**
 * 序列化表单为Object
 */
$.fn.serializeObject = function () {
  var o = {};
  var a = this.serializeArray();
  $.each(a, function () {
    if (o[this.name] !== undefined) {
      if (!o[this.name].push) {
        o[this.name] = [o[this.name]];
      }
      o[this.name].push(this.value || '');
    } else {
      o[this.name] = this.value || '';
    }
  });
  return o;
};

var ajaxCount = 0;

/**
 * Ajax封装
 *
 * type 请求类型
 * url 请求地址
 * param 请求参数
 * async 同异步
 * cache 缓存
 * showSuccessMsg 成功是否显示消息
 * @param obj 参数对象
 * @param callback 回调函数
 */
function jsonAjax(obj, callback) {
  ajaxCount++;
  NProgress.start();
  $.ajax({
    type: obj.type || GLOBAL_CONSTANTS.METHOD.GET,
    url: obj.url,
    data: obj.param,
    contentType: obj.contentType === undefined ? 'application/x-www-form-urlencoded' : obj.contentType,
    async: obj.async === undefined ? true : obj.async,
    cache: obj.cache === undefined ? true : obj.cache
  }).done(function (result) {
    if (result.success) {
      callback(result.data);
      if (obj.showSuccessMsg && obj.showSuccessMsg != null) {
        if (result.message == null) {
          result.message = "操作成功";
        }
        toastr.success(result.message);
      }
    } else {
      if (result.message == null) {
        result.message = "发生未知错误！";
      }
      toastr.error(result.message);
      if (result.httpStatusCode == 401) {
        setTimeout(function () {
          window.location.reload();
        }, 2000)
      }
    }
  }).fail(function (xhr, error) {
    if (xhr.responseJSON == undefined) {
      toastr.error('系统异常，请稍后重试！');
      return false;
    }
    toastr.error(xhr.responseJSON.message);
    if (xhr.status == 401) {
      setTimeout(function () {
        window.location.reload();
      }, 2000)
    }
  }).always(function (results) {
    ajaxCount--;
    if (ajaxCount == 0) {
      NProgress.done();
    }
  });
}

/**
 * Ajax封装（文件上传）
 *
 * type 请求类型
 * url 请求地址
 * param 请求参数
 * async 同异步
 * cache 缓存
 * showSuccessMsg 成功是否显示消息
 * @param obj 参数对象
 * @param callback 回调函数
 */
function fileJsonAjax(obj, callback) {
  ajaxCount++;
  NProgress.start();
  $.ajax({
    type: obj.type || GLOBAL_CONSTANTS.METHOD.POST,
    url: obj.url,
    data: obj.param,
    async: obj.async === undefined ? true : obj.async,
    contentType: false,
    processData: false
  }).done(function (result) {
    if (result.success) {
      callback(result.data);
      if (obj.showSuccessMsg && obj.showSuccessMsg != null) {
        if (result.message == null) {
          result.message = "操作成功";
        }
        toastr.success(result.message);
      }
    } else {
      if (result.message == null) {
        result.message = "发生未知错误！";
      }
      toastr.error(result.message);
      if (result.httpStatusCode == 401) {
        setTimeout(function () {
          window.location.reload();
        }, 2000)
      }
    }
  }).fail(function (xhr, error) {
    if (xhr.responseJSON == undefined) {
      toastr.error('系统异常，请稍后重试！');
      return false;
    }
    toastr.error(xhr.responseJSON.message);
    if (xhr.status == 401) {
      setTimeout(function () {
        window.location.reload();
      }, 2000)
    }
  }).always(function () {
    ajaxCount--;
    if (ajaxCount == 0) {
      NProgress.done();
    }
  });
}

var GLOBAL_CONSTANTS = {
  ZERO: 0,
  ONE: 1,
  PAGE_SZIE: 5,
  DEFAULT_PAGE_SZIE: 5,
  DEFAULT_PAGE_SIZE_FIFTEEN: 15,
  DEFAULT_ICON_DISP_PAGE_SIZE: 20,
  METHOD: {
    GET: 'get',
    POST: 'post',
    PUT: 'put',
    DELETE: 'delete'
  },
  DEFAULT_ORDER_DIRECTION: 1
};

var session_user = {
  url: '/sports/user/session',
  method: {
    logout: 'delete',
    login: 'get'
  }
};

var session = {
  logon: '/login',
  logout: '/index'
};

var img_src = '';

var user_api = '/sports/user';
var coach_api = '/sports/coach';
var course_api = '/sports/course';
var choose_course_api = '/sports/course_info';
var pri_course_api = '/sports/pri_course';
var course_count_api = '/sports/course_count';
var room_api = '/sports/room';


var GET_METHOD = 'get';
var POST_METHOD = 'post';
var PUT_METHOD = 'put';
var DELETE_METHOD = 'delete';

var VeeValidateConfigDefault = {
  errorBagName: 'errors', // change if property conflicts.
  fieldsBagName: 'fields',
  delay: 0,
  locale: 'en',
  dictionary: null,
  strict: true,
  enableAutoClasses: false,
  classNames: {
    touched: 'touched', // the control has been blurred
    untouched: 'untouched', // the control hasn't been blurred
    valid: 'valid', // model is valid
    invalid: 'invalid', // model is invalid
    pristine: 'pristine', // control has not been interacted with
    dirty: 'dirty' // control has been interacted with
  },
  events: 'blur',
  inject: true
};

var userList = [{
  name: '个人设置',
  icon: 'appstore',
  key: 'sub1',
  children: [{
    path: '/user_setting',
    name: '个人设置',
    key: 'sub1-1'
  }, {
    path: '/evaluate',
    name: '评价管理',
    key: 'sub1-2'
  }]
}, {
  path: '',
  name: '课程管理',
  icon: 'alert',
  children: [{
    path: '/course_info',
    name: '选择课程',
    key: 'sub2-1'
  }, {
    path: '/pri_course',
    name: '选择私教',
    key: 'sub2-2'
  }],
  key: 'sub2'
}];

var coachList = [{
  name: '个人设置',
  icon: 'appstore',
  key: 'sub1',
  children: [{
    path: '/coach_setting',
    name: '个人设置',
    key: 'sub1-1'
  }]
}, {
  path: '',
  name: '课程管理',
  icon: 'alert',
  children: [{
    path: '/coach_pri_course',
    name: '发布私教',
    key: 'sub2-1'
  }],
  key: 'sub2'
}];


var menuList = [{
  name: '人员管理',
  icon: 'appstore',
  key: 'sub1',
  children: [{
    path: '/user',
    name: '会员管理',
    key: 'sub1-1'
  }, {
    path: '/coach',
    name: '教练管理',
    key: 'sub1-2'
  }]
}, {
  path: '',
  name: '课程管理',
  icon: 'alert',
  children: [{
    path: '/course',
    name: '课程管理',
    key: 'sub2-1'
  }, {
    path: '/course_group',
    name: '课程分组',
    key: 'sub2-2'
  }],
  key: 'sub2'
}, {
  name: '器材管理',
  icon: 'database',
  key: 'sub3',
  children: [{
    path: '/room',
    name: '房间管理',
    key: 'sub3-1'
  }, {
    path: '/device',
    name: '设备管理',
    key: 'sub3-2'
  }]
}, {
  name: '数据分析',
  icon: 'dashboard',
  key: 'sub5',
  children: [{
    path: '/opr_mgr',
    name: '操作查询',
    key: 'sub5-1'
  }, {
    path: 'data_sys',
    name: '项目总览',
    key: 'sub5-2'
  }]
}, {
  name: '系统管理',
  icon: 'setting',
  key: 'sub6'
  // ,
  // children: [{
  //   path: '',
  //   name: '系统设置',
  //   key: 'sub6-1'
  // }]
}];

/**************************************时间格式化处理************************************/
function dateFtt(fmt, date) { //author: meizz
  var o = {
    "M+": date.getMonth() + 1,                 //月份
    "d+": date.getDate(),                    //日
    "h+": date.getHours(),                   //小时
    "m+": date.getMinutes(),                 //分
    "s+": date.getSeconds(),                 //秒
    "q+": Math.floor((date.getMonth() + 3) / 3), //季度
    "S": date.getMilliseconds()             //毫秒
  };
  if (/(y+)/.test(fmt))
    fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt))
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
}

Date.prototype.Format = function (fmt) { //author: meizz
  var o = {
    "M+": this.getMonth() + 1,                 //月份
    "d+": this.getDate(),                    //日
    "h+": this.getHours(),                   //小时
    "m+": this.getMinutes(),                 //分
    "s+": this.getSeconds(),                 //秒
    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
    "S": this.getMilliseconds()             //毫秒
  };
  if (/(y+)/.test(fmt))
    fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt))
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
};

