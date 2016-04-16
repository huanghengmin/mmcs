/**
 * ע������
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
            blankText:'�����Ϊ��!'
        },
        items : [{
            id :'userName.info',
            fieldLabel:'������',
            xtype:'displayfield'
        },{
            id:'userName.hidden.info',
            name:'account.userName',
            xtype:'hidden'
        },{
            id :'userId.info',
            fieldLabel:"���������֤��",
            xtype:'displayfield'
        },{
            id:'userId.hidden.info',
            name:'account.userId',
            xtype:'hidden'
        },{
            fieldLabel:"������Email",
            name:'account.email',
            regex:/^\w+[\w.]*@[\w.]+\.\w+$/,
            regexText:'������(��:hello@hzih.com)',
            emptyText:'������(��:hello@hzih.com)'
        },{
            fieldLabel:"�������ֻ�",
            name:'account.mobilePhone',
            regex:/^.{11}$/,
            regexText:'������(��:13838168168)',
            emptyText:'������(��:13838168168)'
        },{
            fieldLabel:"�����˵绰",
            name:'account.phone',
            regex:/^.{2,100}$/,
            regexText:'������(��:0571-88880571)',
            emptyText:'������(��:0571-88880571)'
        },{
            id :'districtCode.info',
            fieldLabel:"�������",
            xtype:'displayfield'
        },{
            id:'districtCode.hidden.info',
            name:'account.districtCode',
            xtype:'hidden'
        },{
            id :'systemId.info',
            fieldLabel:"����������ƽ̨���",
            name:'account.systemId',
            regex:/^.{8}$/,
            regexText:'������������ƽ̨���,���Զ����ֵ��ַ���������2λ',
            emptyText:'������8λ��������ƽ̨���'
        },{
            name:'account.status',
            xtype:'hidden',
            value:'0'
        },{
            id:'orgCode.info',
            fieldLabel:"�����˵�λ",
            xtype:'displayfield'
        },{
            id:'orgCode.hidden.info',
            name:'account.orgCode',
            xtype:'hidden'
        },{
            fieldLabel:"�����ɫ",hiddenName:'role.id',
            xtype:'combo',
            mode:'local',
            emptyText :'--��ѡ��--',
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
                text:"����",
                id:'init.save.info',
                handler:function(){
                    var userName = Ext.getCmp('userName.hidden.info').getValue();
                    if(userName==undefined||userName==''||userName==null) {
                        Ext.MessageBox.show({
                            title:'��Ϣ',
                            msg:'����֤�鲻�Ǳ�������,�����֤�����ע��!',
                            animEl:'init.save.info',
                            width:300,
//                            buttons:{'ok':'ȷ��'},
                            icon:Ext.MessageBox.WARNING,
                            closable:false
                        });
                    }
                    if (fp.form.isValid()) {
                        fp.getForm().submit({
                            url :registerUrl,
                            method :'POST',
                            waitTitle :'ϵͳ��ʾ',
                            waitMsg :'���ڱ���,���Ժ�...',
                            success : function(form,action) {
                                var msg = action.result.msg;
                                Ext.MessageBox.show({
                                	title:'��Ϣ',
                                    msg:msg,
                                    animEl:'init.save.info',
                                    width:300,
                                    buttons:{'ok':'ȷ��'},
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
                        	title:'��Ϣ',
                            msg:'����ʧ��,����д����ٱ���!',
                            animEl:'init.save.info',
                            width:300,
                            buttons:{'ok':'ȷ��'},
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
            title:'ע������',
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
                title:'��Ϣ',
                msg:'����֤�鲻�Ǳ�������,�����֤�����ע��!',
//                width:300,
//                buttons:{'ok':'ȷ��'},
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
