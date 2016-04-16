
Ext.onReady(function() {
    Ext.BLANK_IMAGE_URL = '../../ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var initUrl = '../../InitSystemAction_initSystem.action';
    var loadUrl = '../../InitSystemAction_load.action';

    var districtRecord = new Ext.data.Record.create([{name:'value',mapping:'value'}, {name:'key',mapping:'key'}]);
    var districtStore = new Ext.data.Store({
        url:"../../DistrictAction_loadKeyValueParent.action",
        method:'POST',
        reader:new Ext.data.JsonReader({ totalProperty:'total',root:'rows'},districtRecord),
        listeners:{
            load:function(){
                Ext.getCmp('province.info').setValue(Ext.getCmp('province.info').getValue());
            }
        }
    });
    districtStore.load();
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
            id :'province.info',
            fieldLabel:"指定省份",
            hiddenName:'initSystem.rootCode',
            xtype:'combo',
            mode:'remote',
            emptyText :'--请选择--',
            editable : false,
            typeAhead:true,
            forceSelection: true,
            triggerAction:'all',
            displayField:"key",valueField:"value",
            store : districtStore
        }]
    });

	var record = new Ext.data.Record.create([
        {name:'districtId',mapping:'districtId'},
        {name:'districtName',mapping:'districtName'}
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
        autoScroll:true,
        buttons:[
        	new Ext.Toolbar.Spacer({width:125}),
            {
                text:"保存",
                id:'init.save.info',
                handler:function(){
                    if (fp.form.isValid()) {
                        fp.getForm().submit({
                            url :initUrl,
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
                                            store.reload();
                                            location.reload();
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
        autoScroll:true,
        items:[{
            xtype:'fieldset',
            title:'系统初始化',
            width:500,
            items:[fp]
        }]
    });
    var panel2 = new Ext.Panel({
        plain:true,
        width:setWidth(),
        border:false,
        buttonAlign :'left',
        buttons:[
//            new Ext.Toolbar.Spacer({width:130}),
            button
        ]
    });
    new Ext.Viewport({
    	layout:'fit',
    	renderTo:Ext.getBody(),
    	items:[{
            frame:true,
            autoScroll:true,
            items:[panel,panel2]
        }]
    });
    store.load();
    store.on('load',function(){
        var record = store.getAt(0);
        if(record!=undefined){
            var districtId = record.get('districtId');
            Ext.getCmp('province.info').setValue(districtId);
        }
	});
});

function setWidth(){
    return '100%';
}
