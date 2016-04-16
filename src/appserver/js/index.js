/**
 * ���м������
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
            text:'ˢ��',
            iconCls: 'refresh',
            handler: function(){
                var id = tabPanel.getActiveTab().getId();
                window.parent.document.getElementById('frame.'+id).contentWindow.location.reload();
            }
        },{
            xtype: 'tbseparator'
        },{
            id:'logout.tb.info',
            text:'�˳�ϵͳ',
            iconCls: 'logout',
            handler: function(){
                logout();
            }
        },{
            xtype: 'tbseparator'
        },{
            text:'��Ϊ��ҳ',
            iconCls: 'home',
            handler: function(){
                document.getElementById('sethome.info').click();
            }
        },{
            xtype: 'tbseparator'

        },{
            text:'�����ղ�',
            iconCls: 'favorite',
            handler: function(){
                AddFavorite(location.href,document.title);
            }
        },{
            xtype: 'tbseparator'
        },{
            pressed:false,
            text:'����',
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
    Ext.getCmp('msg_title').setText("&nbsp;&nbsp;����!&nbsp;&nbsp;[<font color='blue'>"+userName+"</font>],&nbsp;&nbsp;��ӭ��¼�������!");
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
                Ext.getCmp('where_r_u').setText("&nbsp;&nbsp;��ǰλ��&nbsp;&nbsp;-->&nbsp;&nbsp;<font color='blue'>"+this.getActiveTab().title+"</font>&nbsp;&nbsp;");
            }
        }
    });

    if(userName=='admin') {
        var id = 'init_system';
        var title = 'ϵͳ��ʼ��';
        var jsp = 'system/init_system.jsp';
        addTabPage(tabPanel,title,id,jsp);
    } else if(userName=='authadmin'){
//    } else if(userName=='mmac'){
        var id = 'registerManager';
        var title = 'ע�����';
        var jsp = 'system/registerManager.jsp';
        addTabPage(tabPanel,title,id,jsp);
    } else {
        var myMask = new Ext.LoadMask(Ext.getBody(),{
            msg : '���ڼ���,���Ժ�...',
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
                alert("�˲�����������ܾ���\n�����������ַ�����롰about:config�����س�\nȻ��[signed.applets.codebase_principal_support]����Ϊ'true'");
            }
            var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
            prefs.setCharPref('browser.startup.homepage',vrl);
        }
    }
}

/**
 * ���ü����ղؼ�
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
            alert("�����ղ�ʧ�ܣ���ʹ��Ctrl+D�������");
        }
    }
}

/**
 * �˳���¼
 */
function logout(){
    Ext.MessageBox.show({
        title:'ȷ��',
        msg:"<font color='green'>ȷ���˳�ϵͳ��?</font>",
        animEl:'logout.tb.info',
        buttons:{'ok':'ȷ��','no':'ȡ��'},
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
 * ����һ��tabҳ
 * @param tabPanel
 * @param title
 * @param id
 * @param jsp
 */
function addTabPage(tabPanel,title,id,jsp){
    var tabPage = tabPanel.add({//��̬���tabҳ
        id:id,
        title: title,
        html : '<iframe id="frame.'+id+'" width="100%" height="100%" frameborder="0" src="pages/'+jsp+'"></iframe>'
    });
    tabPanel.setActiveTab(tabPage);//���õ�ǰtabҳ
}

/**
 * ���ض��tabҳ
 * @param tabPanel
 * @param map
 */
function addTabPages(tabPanel,map){
    for(var i=0;i<map.size();i++){
        var temp = map.get(i).split('*');
        var id = temp[0];
        var title = temp[1];
        var jsp = temp[2];
        var tabPage = tabPanel.add({//��̬���tabҳ
            id:id,
            title: title,
            html : '<iframe id="frame.'+id+'" width="100%" height="100%" frameborder="0" src="pages/'+jsp+'"></iframe>'
        });
    }
}

function makePermissionMap(type) {
    var IdMap = new Map();
    IdMap.put(2,"runMonitor*���м��*monitor/runMonitor.jsp");
    IdMap.put(3,"terminalMonitor*�ն˼��*monitor/terminalMonitor.jsp");
    IdMap.put(4,"abnormal*Υ��ͨ��*abnormal/abnormal.jsp");
    IdMap.put(5,"assessment*��������*assessment/assessment.jsp");
    IdMap.put(6,"analysis*ͳ�Ʒ���*analysis/analysis.jsp");
    if(type==1){
        IdMap.put(0,"email*�ռ���*email/managerEmail.jsp");
        IdMap.put(1,"notice*��Ϣ����*notice/managerNotice.jsp");
//        IdMap.put(8,"system*ϵͳ����*system/system.jsp");
        IdMap.put(7,"user*�û���־���*audit/audit_user.jsp");
//        IdMap.put(9,"business*ҵ����־���*audit/audit_business.jsp");
//        IdMap.put(10,"equipment*�豸��־���*audit/audit_equipment.jsp");
//        IdMap.put(11,"os*ϵͳ��־���*audit/audit_os.jsp");
    } else if(type==2||type==3){
        IdMap.put(0,"email*�ռ���*email/email.jsp");
        IdMap.put(1,"notice*��Ϣ����*notice/notice.jsp");
    }
    return IdMap;
}