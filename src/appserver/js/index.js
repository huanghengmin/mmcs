/**
 * 集中监管中心
 */
Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = '../../ext/resources/images/default/s.gif';
	Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';

    var userName = getJspValue('userName.account.info');
    var northBar = new Ext.Toolbar({
//        monitorResize :false,
        width:setWidth(),
        border:false,
        items: [{
            id: 'msg_title',
            xtype: 'tbtext',
            text: ''
        },{
            xtype: 'tbseparator'
        },{
            id:'where_r_u',
            xtype: 'tbtext',
            text:''
        },{
            xtype: 'tbseparator'
        },{
            xtype: "tbfill"
        },
            '<a id="sethome.info" onclick="SetHome(this,window.location)"></a>'
        ,{
            xtype: 'tbseparator'
        },{
            pressed:false,
            text:'刷新',
            iconCls: 'refresh',
            handler: function(){
                var id = tabPanel.getActiveTab().getId();
                window.parent.document.getElementById('frame.'+id).contentWindow.location.reload();
            }
        },{
            xtype: 'tbseparator'
        },{
            id:'logout.tb.info',
            text:'退出系统',
            iconCls: 'logout',
            handler: function(){
                logout();
            }
        },{
            xtype: 'tbseparator'
        },{
            text:'设为首页',
            iconCls: 'home',
            handler: function(){
                document.getElementById('sethome.info').click();
            }
        },{
            xtype: 'tbseparator'

        },{
            text:'加入收藏',
            iconCls: 'favorite',
            handler: function(){
                AddFavorite(location.href,document.title);
            }
        },{
            xtype: 'tbseparator'
        },{
            pressed:false,
            text:'帮助',
            iconCls: 'help',
            handler: function(){
                window.open('help.doc');
            }
        }]
    });
    new Ext.Panel({
        renderTo: 'head.mmcs',
        layout:'fit',
        width:setWidth(),
        border:false,
        height:25,
        items: northBar
    });
    Ext.getCmp('msg_title').setText("&nbsp;&nbsp;您好!&nbsp;&nbsp;[<font color='blue'>"+userName+"</font>],&nbsp;&nbsp;欢迎登录监管中心!");
    var tabPanel = new Ext.TabPanel({
        renderTo: 'body.mmcs',
        resizeTabs: true, // turn on tab resizing
        minTabWidth: 85,
        tabWidth: 100,
        border:false,
//        enableTabScroll: true,
        deferredRender: true,
        activeTab:0,
        width: setWidth(),
        height: setHeight(),
        defaults: { border:false },
        items:[],
        listeners:{
            tabchange:function(){
                Ext.getCmp('where_r_u').setText("&nbsp;&nbsp;当前位置&nbsp;&nbsp;-->&nbsp;&nbsp;<font color='blue'>"+this.getActiveTab().title+"</font>&nbsp;&nbsp;");
            }
        }
    });

    if(userName=='admin') {
        var id = 'init_system';
        var title = '系统初始化';
        var jsp = 'system/init_system.jsp';
        addTabPage(tabPanel,title,id,jsp);
    } else if(userName=='authadmin'){
//    } else if(userName=='mmac'){
        var id = 'registerManager';
        var title = '注册管理';
        var jsp = 'system/registerManager.jsp';
        addTabPage(tabPanel,title,id,jsp);
    } else {
        var myMask = new Ext.LoadMask(Ext.getBody(),{
            msg : '正在加载,请稍后...',
            removeMask : true
        });
        myMask.show();
        Ext.Ajax.request({
            url : 'AccountAction_loadPermission.action',
            params :{userName : userName},
            method:'POST',
            success : function(r,o){
                var respText = Ext.util.JSON.decode(r.responseText);
                var status = respText.status;
                if(status==0||status=='0'){
                    var msg = respText.msg;
                    var temp = msg.split('*');
                    var id = temp[0];
                    var title = temp[1];
                    var jsp = temp[2];
                    addTabPage(tabPanel,title,id,jsp);
                    tabPanel.setActiveTab(0);
                } else if(status==1||status=='1') {
                    var roleType = respText.msg;
                    var map;
                    if(roleType=='1') {
                        map = makePermissionMap(1);
                    } else if(roleType=='2'||roleType=='3'){
                        map = makePermissionMap(Number(roleType));
                    }
                    addTabPages(tabPanel,map);
                    tabPanel.setActiveTab(2);
                }
                myMask.hide();
            }
        });
    }
});
function setHeight(){
	return document.body.clientHeight- 130 - 27 - 80;
//	return "100%";
}

function setWidth(){
    return 991;
//    return "100%";
}

function getJspValue(id){
    return document.getElementById(id).value;
}


function SetHome(obj,vrl){
    try {
        obj.style.behavior="url(#default#homepage)";obj.setHomePage(vrl);
    } catch(e){
        if(window.netscape) {
            try {
                netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
            } catch (e) {
                alert("此操作被浏览器拒绝！\n请在浏览器地址栏输入“about:config”并回车\n然后将[signed.applets.codebase_principal_support]设置为'true'");
            }
            var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
            prefs.setCharPref('browser.startup.homepage',vrl);
        }
    }
}

/**
 * 设置加入收藏夹
 * @param sURL
 * @param sTitle
 * @constructor
 */
function AddFavorite(sURL, sTitle){
    try {
        window.external.addFavorite(sURL, sTitle);
    } catch (e) {
        try {
            window.sidebar.addPanel(sTitle, sURL, "");
        } catch (e) {
            alert("加入收藏失败，请使用Ctrl+D进行添加");
        }
    }
}

/**
 * 退出登录
 */
function logout(){
    Ext.MessageBox.show({
        title:'确认',
        msg:"<font color='green'>确认退出系统吗?</font>",
        animEl:'logout.tb.info',
        buttons:{'ok':'确定','no':'取消'},
        icon:Ext.MessageBox.QUESTION,
        closable:false,
        fn:function(e){
            if(e=='ok'){
                window.location = "LoginAction_logout.action";
            }
        }
    });
}

/**
 * 加载一个tab页
 * @param tabPanel
 * @param title
 * @param id
 * @param jsp
 */
function addTabPage(tabPanel,title,id,jsp){
    var tabPage = tabPanel.add({//动态添加tab页
        id:id,
        title: title,
        html : '<iframe id="frame.'+id+'" width="100%" height="100%" frameborder="0" src="pages/'+jsp+'"></iframe>'
    });
    tabPanel.setActiveTab(tabPage);//设置当前tab页
}

/**
 * 加载多个tab页
 * @param tabPanel
 * @param map
 */
function addTabPages(tabPanel,map){
    for(var i=0;i<map.size();i++){
        var temp = map.get(i).split('*');
        var id = temp[0];
        var title = temp[1];
        var jsp = temp[2];
        var tabPage = tabPanel.add({//动态添加tab页
            id:id,
            title: title,
            html : '<iframe id="frame.'+id+'" width="100%" height="100%" frameborder="0" src="pages/'+jsp+'"></iframe>'
        });
    }
}

function makePermissionMap(type) {
    var IdMap = new Map();
    IdMap.put(2,"runMonitor*运行监控*monitor/runMonitor.jsp");
    IdMap.put(3,"terminalMonitor*终端监控*monitor/terminalMonitor.jsp");
    IdMap.put(4,"abnormal*违规通报*abnormal/abnormal.jsp");
    IdMap.put(5,"assessment*考核评比*assessment/assessment.jsp");
    IdMap.put(6,"analysis*统计分析*analysis/analysis.jsp");
    if(type==1){
        IdMap.put(0,"email*收件箱*email/managerEmail.jsp");
        IdMap.put(1,"notice*信息发布*notice/managerNotice.jsp");
//        IdMap.put(8,"system*系统管理*system/system.jsp");
        IdMap.put(7,"user*用户日志审计*audit/audit_user.jsp");
//        IdMap.put(9,"business*业务日志审计*audit/audit_business.jsp");
//        IdMap.put(10,"equipment*设备日志审计*audit/audit_equipment.jsp");
//        IdMap.put(11,"os*系统日志审计*audit/audit_os.jsp");
    } else if(type==2||type==3){
        IdMap.put(0,"email*收件箱*email/email.jsp");
        IdMap.put(1,"notice*信息发布*notice/notice.jsp");
    }
    return IdMap;
}