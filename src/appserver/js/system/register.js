/**
 * 注册申请
 */
Ext.onReady(function() {
    Ext.BLANK_IMAGE_URL = '../../ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var registerUrl = '../../RegisterAction_register.action';
    var loadUrl = '../../RegisterAction_load.action';

    var recordRole = new Ext.data.Record.create([{name:'value',mapping:'value'}, {name:'key',mapping:'key'}]);
    var storeRole = new Ext.data.Store({
        url:"../../RoleAction_readNameKeyValue.action",
        reader:new Ext.data.JsonReader({ totalProperty:'total',root:"rows"},recordRole)
    });
    var recordDepartment = new Ext.data.Record.create([{name:'value',mapping:'value'}, {name:'key',mapping:'key'}]);
    var storeDepartment = new Ext.data.Store({
        url:"../../AccountAction_queryKeyValueDepartment.action",
        reader:new Ext.data.JsonReader({ totalProperty:'total',root:"rows"},recordDepartment)
    });

    var fp = new Ext.form.FormPanel({
        plain:true,
        labelAlign:'right',
        labelWidth:130,
        defaultType:'textfield',
        defaults:{
            width:200,
            allowBlank:false,
            blankText:'该项不能为空!'
        },
        items : [{
            id :'userName.info',
            fieldLabel:'申请人',
            xtype:'displayfield'
        },{
            id:'userName.hidden.info',
            name:'account.userName',
            xtype:'hidden'
        },{
            id :'userId.info',
            fieldLabel:"申请人身份证号",
            xtype:'displayfield'
        },{
            id:'userId.hidden.info',
            name:'account.userId',
            xtype:'hidden'
        },{
            fieldLabel:"申请人Email",
            name:'account.email',
            regex:/^\w+[\w.]*@[\w.]+\.\w+$/,
            regexText:'请输入(例:hello@hzih.com)',
            emptyText:'请输入(例:hello@hzih.com)'
        },{
            fieldLabel:"申请人手机",
            name:'account.mobilePhone',
            regex:/^.{11}$/,
            regexText:'请输入(例:13838168168)',
            emptyText:'请输入(例:13838168168)'
        },{
            fieldLabel:"申请人电话",
            name:'account.phone',
            regex:/^.{2,100}$/,
            regexText:'请输入(例:0571-88880571)',
            emptyText:'请输入(例:0571-88880571)'
        },{
            id :'districtCode.info',
            fieldLabel:"申请地区",
            xtype:'displayfield'
        },{
            id:'districtCode.hidden.info',
            name:'account.districtCode',
            xtype:'hidden'
        },{
            id :'systemId.info',
            fieldLabel:"申请人所在平台编号",
            name:'account.systemId',
            regex:/^.{8}$/,
            regexText:'请输入您所在平台编号,在自动出现的字符后面填上2位',
            emptyText:'请输入8位的您所在平台编号'
        },{
            name:'account.status',
            xtype:'hidden',
            value:'0'
        },{
            id:'orgCode.info',
            fieldLabel:"申请人单位",
            xtype:'displayfield'
        },{
            id:'orgCode.hidden.info',
            name:'account.orgCode',
            xtype:'hidden'
        },{
            fieldLabel:"申请角色",hiddenName:'role.id',
            xtype:'combo',
            mode:'local',
            emptyText :'--请选择--',
            editable : false,
            typeAhead:true,
            forceSelection: true,
            triggerAction:'all',
            displayField:"key",valueField:"value",
            store:storeRole
        }]
    });

	var record = new Ext.data.Record.create([
        {name:'districtCode',mapping:'districtCode'},
        {name:'userName',mapping:'userName'},
        {name:'userId',mapping:'userId'},
        {name:'systemId',mapping:'systemId'},
        {name:'email',mapping:'email'},
        {name:'phone',mapping:'phone'},
        {name:'districtName',mapping:'districtName'},
        {name:'orgName',mapping:'orgName'},
        {name:'orgCode',mapping:'orgCode'}
    ]);
    var proxy = new Ext.data.HttpProxy({
        url:loadUrl
    });
    var reader = new Ext.data.JsonReader({
        totalProperty:"total",
        root:'rows'
    },record);
    var store = new Ext.data.Store({
        proxy : proxy,
        reader : reader
    });

    var button = new Ext.Panel({
        plain:true,
        buttonAlign :'left',
        buttons:[
        	new Ext.Toolbar.Spacer({width:125}),
            {
                text:"保存",
                id:'init.save.info',
                handler:function(){
                    var userName = Ext.getCmp('userName.hidden.info').getValue();
                    if(userName==undefined||userName==''||userName==null) {
                        Ext.MessageBox.show({
                            title:'信息',
                            msg:'您的证书不是本地区的,请更换证书后再注册!',
                            animEl:'init.save.info',
                            width:300,
//                            buttons:{'ok':'确定'},
                            icon:Ext.MessageBox.WARNING,
                            closable:false
                        });
                    }
                    if (fp.form.isValid()) {
                        fp.getForm().submit({
                            url :registerUrl,
                            method :'POST',
                            waitTitle :'系统提示',
                            waitMsg :'正在保存,请稍后...',
                            success : function(form,action) {
                                var msg = action.result.msg;
                                Ext.MessageBox.show({
                                	title:'信息',
                                    msg:msg,
                                    animEl:'init.save.info',
                                    width:300,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.INFO,
                                    closable:false,
                                    fn:function(e){
                                        if(e=='ok'){
                                            window.parent.location.reload();
                                        }
                                    }
                                });
                            }
                        });
                    } else {
                        Ext.MessageBox.show({
                        	title:'信息',
                            msg:'保存失败,请填写完成再保存!',
                            animEl:'init.save.info',
                            width:300,
                            buttons:{'ok':'确定'},
                            icon:Ext.MessageBox.ERROR,
                            closable:false
                        });
                    }
                }
            }
        ]
    });
    var panel = new Ext.Panel({
        plain:true,
        width:setWidth(),
        border:false,
        items:[{
            xtype:'fieldset',
            title:'注册申请',
            width:500,
            items:[fp]
        }]
    });
    var panel2 = new Ext.Panel({
        plain:true,
        width:setWidth(),
        border:false,
        buttonAlign :'left',
        buttons:[button]
    });
    new Ext.Viewport({
    	layout:'fit',
    	renderTo:Ext.getBody(),
    	items:[{
            frame:true,
            items:[panel,panel2]
        }]
    });
    store.load();
    storeRole.load();
    storeDepartment.load();
    store.on('load',function(){
        if(store.getCount()==0){
            Ext.MessageBox.show({
                title:'信息',
                msg:'您的证书不是本地区的,请更换证书后再注册!',
//                width:300,
//                buttons:{'ok':'确定'},
                icon:Ext.MessageBox.ERROR,
                closable:false
            });
        } else {
            var record = store.getAt(0);
            if(record!=undefined){
                var districtCode = record.get('districtCode');
                var districtName = record.get('districtName');
                var userName = record.get('userName');
                var userId = record.get('userId');
                var systemId = record.get('systemId');
                var orgCode = record.get('orgCode');
                var orgName = record.get('orgName');
                Ext.getCmp('districtCode.info').setValue(districtName);
                Ext.getCmp('userName.info').setValue(userName);
                Ext.getCmp('userId.info').setValue(userId);
                Ext.getCmp('systemId.info').setValue(systemId);
                Ext.getCmp('orgCode.info').setValue(orgName);
                Ext.getCmp('districtCode.hidden.info').setValue(districtCode);
                Ext.getCmp('userName.hidden.info').setValue(userName);
                Ext.getCmp('userId.hidden.info').setValue(userId);
                Ext.getCmp('orgCode.hidden.info').setValue(orgCode);
            }
        }
	});
});

function setWidth(){
    return 991;
}
