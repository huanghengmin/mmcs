/**
 * ע�����
 */
Ext.onReady(function() {
    Ext.BLANK_IMAGE_URL = '../../ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var loadUrl = '../../AccountAction_load.action';
	var record = new Ext.data.Record.create([
        {name:'id',mapping:'id'},
        {name:'districtCode',mapping:'districtCode'},
        {name:'userName',mapping:'userName'},
        {name:'roleName',mapping:'roleName'},
        {name:'roleId',mapping:'roleId'},
        {name:'userId',mapping:'userId'},
        {name:'systemId',mapping:'systemId'},
        {name:'email',mapping:'email'},
        {name:'phone',mapping:'phone'},
        {name:'mobilePhone',mapping:'mobilePhone'},
        {name:'orgCode',mapping:'orgCode'},
        {name:'orgName',mapping:'orgName'},
        {name:'status',mapping:'status'},
        {name:'registerTime',mapping:'registerTime'},
        {name:'modifiedTime',mapping:'modifiedTime'},
        {name:'districtName',mapping:'districtName'}
    ]);
    var proxy = new Ext.data.HttpProxy({
        url:loadUrl
    });
    var reader = new Ext.data.JsonReader({
        totalProperty:"total",
        root:'rows'
    },record);
    var store = new Ext.data.GroupingStore({
        proxy : proxy,
        reader : reader
    });
    var start = 0;
    var pageSize = gridPageSize();
    var boxM = new Ext.grid.CheckboxSelectionModel();   //��ѡ��
    var rowNumber = new Ext.grid.RowNumberer();         //�Զ� ���
    var colM = new Ext.grid.ColumnModel([
        boxM,
        rowNumber,
        {header:"����������",		dataIndex:"userName",  align:'center',sortable:true,width:45},
        {header:"�����ɫ",		dataIndex:"roleName",  align:'center',sortable:true,width:45},
//        {header:"���������֤��",	dataIndex:"userId",  align:'center',sortable:true,width:45},
        {header:"���������ڵ���",		dataIndex:"districtName",  align:'center',sortable:true,width:45},
        {header:"�����˵绰",		dataIndex:"phone",  align:'center',sortable:true,width:45},
//        {header:"����������",		dataIndex:"email",  align:'center',sortable:true,width:45},
        {header:"����ʱ��",		dataIndex:"registerTime",  align:'center',sortable:true,width:45},
        {header:"���ͨ��ʱ��",	dataIndex:"modifiedTime",  align:'center',sortable:true,width:45,renderer:modified_showUrl},
//        {header:"����״̬",		dataIndex:"status",  align:'center',sortable:true,width:45,renderer:status_showUrl},
        {header:"�������",		dataIndex:"status",  align:'center',sortable:true,width:45,renderer:flag_showUrl}
    ]);
    var page_toolbar = new Ext.PagingToolbar({
        pageSize : pageSize,
        store:store,
        displayInfo:true,
        displayMsg:"��ʾ��{0}����¼����{1}����¼��һ��{2}��",
        emptyMsg:"û�м�¼",
        beforePageText:"��ǰҳ",
        afterPageText:"��{0}ҳ"
    });
    var grid = new Ext.grid.GridPanel({
        id:'grid.info',
        height:setHeight(),
        width:setWidth(),
        loadMask:{msg:'���ڼ������ݣ����Ժ�...'},
        border:false,
        collapsible:false,
        cm:colM,
        sm:boxM,
        store:store,
        stripeRows:true,
        disableSelection:true,
        bodyStyle:'width:100%',
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        viewConfig:{
            forceFit:true,
            enableRowBody:true,
            getRowClass:function(record,rowIndex,p,store){
                return 'x-grid3-row-collapsed';
            }
        },
        layout:'fit',
        tbar:['��ʼ:',{
            id : 'startDate.tb.info',
            xtype : 'datefield',
            name : 'startDate',
            emptyText : '�����������',
            format : 'Y-m-d'
        }, {
            xtype : 'tbseparator'
        },'����:',{
            id : 'endDate.tb.info',
            xtype : 'datefield',
            name : 'endDate',
            emptyText : '�����������',
            format : 'Y-m-d'
        }, {
            xtype : 'tbseparator'
        },'�û���:',{
            id:'userName.tb.info',
            xtype:'textfield',
            emptyText:'�������û���',
            width : 100
        },{
            xtype : 'tbseparator'
        },'����:',{
            id : 'status.tb.info',
            xtype : 'combo',
            store : new Ext.data.ArrayStore({
                autoDestroy : true,
                fields : ['value', 'key'],
                data : [
                    ['0', 'δͨ�����'],
                    ['1', 'ͨ�����'],
                    ['2', '�Ƿ�ע��']
                ]
            }),
            valueField : 'value',
            displayField : 'key',
            mode : 'local',
            forceSelection : true,
            triggerAction : 'all',
            emptyText : '--��ѡ��--',
            value : '',
            selectOnFocus : true,
            width : 100
        }, {
            xtype : 'tbseparator'
        }, {
            text : '��ѯ',
            iconCls:'query',
            listeners : {
                click : function() {
                    var status = Ext.fly('status.tb.info').dom.value == '--��ѡ��--'
                        ? null
                        : Ext.getCmp('status.tb.info').getValue();
                    var startDate = Ext.fly("startDate.tb.info").dom.value == '�����������'
                        ? null
                        : Ext.fly('startDate.tb.info').dom.value;
                    var endDate = Ext.fly('endDate.tb.info').dom.value == '�����������'
                        ? null
                        : Ext.fly('endDate.tb.info').dom.value;
                    var userName = Ext.fly('userName.tb.info').dom.value == '�������û���'
                        ? null
                        :Ext.getCmp('userName.tb.info').getValue();
                    store.setBaseParam('startDate', startDate);
                    store.setBaseParam('endDate', endDate);
                    store.setBaseParam('status', status);
                    store.setBaseParam('userName', userName);
                    store.load({params : { start : start,limit : pageSize }});
                }
            }
        },{
            xtype : 'tbseparator'
        /*},{
            id:'btnAdd.info',
            text:'����',
            iconCls:'add',
            handler:function(){
                insert_win(grid,store);
            }
        },{
            xtype : 'tbseparator'*/
        },{
            id:'btnremove.info',
            text:'ɾ��',
            iconCls:'remove',
            handler:function(){
                delete_rows(grid,store);
            }
        },{
            xtype : 'tbseparator'
        },{
            xtype: "tbfill"
        }],
        view:new Ext.grid.GroupingView({
            forceFit:true,
            groupingTextTpl:'{text}({[values.rs.length]}����¼)'
        }),
        bbar:page_toolbar
    });
    new Ext.Viewport({
    	layout:'fit',
    	renderTo:Ext.getBody(),
    	items:[grid]
    });
    store.load({params:{start:start,limit:pageSize}});
});

function setHeight(){
	var h = document.body.clientHeight-8;
	return h;
}
function gridPageSize(){
    var size = setHeight()/25;
    return parseInt(size);
}
function setWidth(){
    return 991;
}
function status_showUrl(value){
    if(value=='0') {
        return '<font color="red">δͨ�����</font>';
    } else if(value=='1') {
        return '<font color="green">ͨ�����</font>';
    } else {
        return '<font color="yellow">�Ƿ�</font>';
    }
}

function modified_showUrl(value) {
    if(value.length==0||value==undefined) {
        return '<font color="red">δͨ�����</font>';
    } else {
        return value;
    }
}

function flag_showUrl(value) {
    if(value=='0'){
        return '<a href="javascript:;" onclick="query_detail();" >��ϸ</a>&nbsp;&nbsp;' +
            '<a href="javascript:;" onclick="update_win();" >�޸�</a>&nbsp;&nbsp;' +
            '<a href="javascript:;" onclick="allow();" >ͨ��</a>';
    } else if(value=='1') {
        return '<a href="javascript:;" onclick="query_detail();" >��ϸ</a>&nbsp;&nbsp;' +
            '<a href="javascript:;" onclick="update_win();" >�޸�</a>&nbsp;&nbsp;' +
            '<font color="gray">ͨ��</font>';
    }
}
var recordRole = new Ext.data.Record.create([{name:'value',mapping:'value'}, {name:'key',mapping:'key'}]);

function query_detail(){
    var grid = Ext.getCmp('grid.info');
    var selModel = grid.getSelectionModel();
    var formPanel;
    if(selModel.hasSelection()){
        var selections = selModel.getSelections();
        Ext.each(selections,function(item){
            formPanel = new Ext.form.FormPanel({
                frame:true,
                labelAlign:'right',
                labelWidth:130,
                defaultType:'displayfield',
                defaults:{
                    width:200,
                    allowBlank:false,
                    blankText:'�����Ϊ��!'
                },
                items : [{
                    fieldLabel:"������",
                    value:item.data.userName
                },{
                    fieldLabel:"���������֤��",
                    value:item.data.userId
                },{
                    fieldLabel:"������Email",
                    value:item.data.email
                },{
                    fieldLabel:"�����˵绰",
                    value:item.data.phone
                },{
                    fieldLabel:"�������ֻ�",
                    value:item.data.mobilePhone
                },{
                    fieldLabel:"�������",
                    value:item.data.districtName
                },{
                    fieldLabel:"���뵥λ",
                    value:item.data.orgName
                },{
                    fieldLabel:"����������ƽ̨���",
                    value:item.data.systemId
                },{
                    fieldLabel:"�����ɫ",
                    value:item.data.roleName
                },{
                    fieldLabel:"����ʱ��",
                    value:item.data.registerTime
                },{
                    fieldLabel:"���ͨ��ʱ��",
                    value:modified_showUrl(item.data.modifiedTime)
                }]
            });
        });
    }
    var win = new Ext.Window({
        title:"��ϸ��Ϣ-������ע����Ϣ",
        width:400,
        height:360,
        layout:'fit',
        modal:true,
        items: [formPanel],
        bbar:[
            new Ext.Toolbar.Fill(),
            new Ext.Button ({
                text : '����',
                allowDepress : false,
                handler : function() {win.close();}
            }),
            new Ext.Button ({
                allowDepress : false,
                text : '�ر�',
                handler : function() {win.close();}
            })
        ]
    }).show();
}

function allow(){
    var grid = Ext.getCmp('grid.info');
    var store = grid.getStore();
    var selModel = grid.getSelectionModel();
    if(selModel.hasSelection()){
        var selections = selModel.getSelections();
        Ext.each(selections,function(item){
            var userName = item.data.userName;
            Ext.MessageBox.show({
                title:'��Ϣ',
                msg:'<font color="green">ȷ��Ҫͨ��'+userName+'�����룿</font>',
                width:250,
                buttons:{'ok':'ȷ��','no':'ȡ��'},
                icon:Ext.MessageBox.WARNING,
                closable:false,
                fn:function(e){
                    if(e == 'ok'){
                        var myMask = new Ext.LoadMask(Ext.getBody(),{
                            msg:'���ڴ���,���Ժ�...',
                            removeMask:true
                        });
                        myMask.show();
                        Ext.Ajax.request({
                            url : '../../RegisterAction_registerAllow.action',
                            params :{userName : userName},
                            success : function(r,o){
                                myMask.hide();
                                var respText = Ext.util.JSON.decode(r.responseText);
                                var msg = respText.msg;
                                grid.render();
                                store.reload();
                                Ext.MessageBox.show({
                                    title:'��Ϣ',
                                    msg:msg,
                                    animEl:'btnRemove.db.external.info',
                                    buttons:{'ok':'ȷ��'},
                                    icon:Ext.MessageBox.INFO,
                                    closable:false
                                });
                            }
                        });
                    }
                }
            });
        });
    }
}

function insert_win(){
    var storeRole = new Ext.data.Store({
        url:"../../RoleAction_readNameKeyValue.action",
        reader:new Ext.data.JsonReader({ totalProperty:'total',root:"rows"},recordRole)
    });
}

function delete_rows(grid,store){
    var selModel = grid.getSelectionModel();
    var count = selModel.getCount();
    if(count==0){
        Ext.MessageBox.show({
            title:'��Ϣ',
            msg:'<font color="green">��û�й�ѡ�κμ�¼!</font>',
            animEl:'export.info',
            buttons:{'ok':'ȷ��'},
            icon:Ext.MessageBox.INFO,
            closable:false
        });
    }else if(count > 0){
        Ext.MessageBox.show({
            title:'��Ϣ',
            msg:'<font color="green">ȷ��Ҫɾ����ѡ��¼��</font>',
            animEl:'export.info',
            buttons:{'ok':'ȷ��','no':'ȡ��'},
            icon:Ext.MessageBox.INFO,
            closable:false,
            fn:function(e){
                if(e == 'ok'){
                    var myMask = new Ext.LoadMask(Ext.getBody(),{
                        msg:'����ɾ��,���Ժ�...',
                        removeMask : true
                    });
                    myMask.show();
                    var ids = new Array();
                    var record = grid.getSelectionModel().getSelections();
                    for(var i = 0; i < record.length; i++){
                        ids[i] = record[i].get('id');
                    }
                    Ext.Ajax.request({
                        url : '../../RegisterAction_delete.action',
                        params :{ids : ids},
                        success : function(action){
                            var json = Ext.decode(action.responseText);
                            myMask.hide();
                            Ext.MessageBox.show({
                                title:'��Ϣ',
                                msg:json.msg,
                                animEl:'export.info',
                                buttons:{'ok':'ȷ��'},
                                icon:Ext.MessageBox.INFO,
                                closable:false,
		                        fn:function(e){
		                        	if(e=='ok'){
		                            	grid.render();
		                            	store.reload();
		                        	}
		                        }
                            });
                        }
                    });
                }
            }
        });
    }
}

function update_win(){
    var storeRole = new Ext.data.Store({
        url:"../../RoleAction_readNameKeyValue.action",
        reader:new Ext.data.JsonReader({ totalProperty:'total',root:"rows"},recordRole),
        listeners:{
            load:function(){
                var roleId = Ext.getCmp('roleId.info').getValue();
                Ext.getCmp('roleId.info').setValue(roleId);
            }
        }
    });
    storeRole.load();
    var grid = Ext.getCmp('grid.info');
    var store = grid.getStore();
    var selModel = grid.getSelectionModel();
    var formPanel
    if(selModel.hasSelection()){
        var selections = selModel.getSelections();
        Ext.each(selections,function(item){
            formPanel= new Ext.form.FormPanel({
                frame:true,
                labelAlign:'right',
                labelWidth:130,
                defaultType:'textfield',
                defaults:{
                    width:200,
                    allowBlank:false,
                    blankText:'�����Ϊ��!'
                },
                items : [{
                    name:'account.id',
                    xtype:'hidden',
                    value:item.data.id
                },{
                    fieldLabel:'������',
                    xtype:'displayfield',
                    value:item.data.userName
                },{
                    name:'account.userName',
                    xtype:'hidden',
                    value:item.data.userName
                },{
                    fieldLabel:"���������֤��",
                    name:'account.userId',
                    value:item.data.userId
                },{
                    fieldLabel:"������Email",
                    name:'account.email',
                    value:item.data.email,
                    regex:/^\w+[\w.]*@[\w.]+\.\w+$/,
                    regexText:'������(��:hello@hzih.com)',
                    emptyText:'������(��:hello@hzih.com)'
                },{
                    fieldLabel:"�������ֻ�",
                    name:'account.mobilePhone',
                    value:item.data.mobilePhone,
                    regex:/^.{11}$/,
                    regexText:'������(��:13838168168)',
                    emptyText:'������(��:13838168168)'
                },{
                    fieldLabel:"�����˵绰",
                    name:'account.phone',
                    value:item.data.phone,
                    regex:/^.{2,100}$/,
                    regexText:'������(��:0571-88880571)',
                    emptyText:'������(��:0571-88880571)'
                },{
                    fieldLabel:"�������",
                    xtype:'displayfield',
                    value:item.data.districtName
                },{
                    name:'account.districtCode',
                    xtype:'hidden',
                    value:item.data.districtCode
                },{
                    fieldLabel:"�����˵�λ",
                    xtype:'displayfield',
                    value:item.data.orgName
                },{
                    name:'account.orgCode',
                    xtype:'hidden',
                    value:item.data.orgCode
                },{
                    fieldLabel:"����������ƽ̨���",
                    name:'account.systemId',
                    value:item.data.systemId,
                    regex:/^.{8}$/,
                    regexText:'������������ƽ̨���,���Զ����ֵ��ַ���������2λ',
                    emptyText:'������8λ��������ƽ̨���'
                },{
                    name:'account.status',
                    xtype:'hidden',
                    value:item.data.status
                },{
                    id:'roleId.info',
                    fieldLabel:"�����ɫ",hiddenName:'role.id',value:item.data.roleId,
                    xtype:'combo',
                    mode:'local',
                    emptyText :'--��ѡ��--',
                    editable : false,
                    typeAhead:true,
                    forceSelection: true,
                    triggerAction:'all',
                    displayField:"key",valueField:"value",
                    store:storeRole
                },{
                    fieldLabel:"����ʱ��",
                    xtype:'displayfield',
                    value:item.data.registerTime
                },{
                    fieldLabel:"���ͨ��ʱ��",
                    xtype:'displayfield',
                    value:modified_showUrl(item.data.modifiedTime)
                }]
            });
        });
    }
    var win = new Ext.Window({
        title:"�޸���Ϣ",
        width:400,
        layout:'fit',
        height:360,
        modal:true,
        items:[formPanel],
        bbar:['->',{
            id:'update_win.info',
            text:'�޸�',
            handler:function(){
                Ext.MessageBox.show({
                    title:'��Ϣ',
                    msg:'<font color="green">�Ƿ��޸Ľ�ɫ��Ϣ?</font>',
                    width:250,
                    animEl:'update_win.info',
                    buttons:{'ok':'����','no':'ȡ��'},
                    icon:Ext.MessageBox.WARNING,
                    closable:false,
                    fn:function(e){
                        if(e=='ok'){
                            if (formPanel.form.isValid()) {
                                formPanel.getForm().submit({
                                    url :'RegisterAction_update.action',
                                    method :'POST',
                                    waitTitle :'ϵͳ��ʾ',
                                    waitMsg :'�����޸�,���Ժ�...',
                                    success : function(form,action) {
                                        var msg = action.result.msg;
                                        Ext.MessageBox.show({
                                            title:'��Ϣ',
                                            msg:msg,
                                            animEl:'update_win.info',
                                            width:300,
                                            buttons:{'ok':'ȷ��','no':'ȡ��'},
                                            icon:Ext.MessageBox.INFO,
                                            closable:false,
                                            fn:function(e){
                                                if(e=='ok'){
                                                    grid.render();
                                                    store.reload();
                                                    win.close();
                                                }
                                            }
                                        });
                                    }
                                });
                            } else {
                                Ext.MessageBox.show({
                                    title:'��Ϣ',
                                    msg:'�޸�ʧ��,����д����ٱ���!',
                                    animEl:'update_win.info',
                                    width:300,
                                    buttons:{'ok':'ȷ��'},
                                    icon:Ext.MessageBox.ERROR,
                                    closable:false
                                });
                            }
                        }
                    }
                });
            }
        },{
            text:'�ر�',
            handler:function(){
                win.close();
            }
        }
        ]
    }).show();
}
