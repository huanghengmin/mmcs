/**
 * 注册管理
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
    var boxM = new Ext.grid.CheckboxSelectionModel();   //复选框
    var rowNumber = new Ext.grid.RowNumberer();         //自动 编号
    var colM = new Ext.grid.ColumnModel([
        boxM,
        rowNumber,
        {header:"申请人名称",		dataIndex:"userName",  align:'center',sortable:true,width:45},
        {header:"申请角色",		dataIndex:"roleName",  align:'center',sortable:true,width:45},
//        {header:"申请人身份证号",	dataIndex:"userId",  align:'center',sortable:true,width:45},
        {header:"申请人所在地区",		dataIndex:"districtName",  align:'center',sortable:true,width:45},
        {header:"申请人电话",		dataIndex:"phone",  align:'center',sortable:true,width:45},
//        {header:"申请人邮箱",		dataIndex:"email",  align:'center',sortable:true,width:45},
        {header:"申请时间",		dataIndex:"registerTime",  align:'center',sortable:true,width:45},
        {header:"审核通过时间",	dataIndex:"modifiedTime",  align:'center',sortable:true,width:45,renderer:modified_showUrl},
//        {header:"申请状态",		dataIndex:"status",  align:'center',sortable:true,width:45,renderer:status_showUrl},
        {header:"操作标记",		dataIndex:"status",  align:'center',sortable:true,width:45,renderer:flag_showUrl}
    ]);
    var page_toolbar = new Ext.PagingToolbar({
        pageSize : pageSize,
        store:store,
        displayInfo:true,
        displayMsg:"显示第{0}条记录到第{1}条记录，一共{2}条",
        emptyMsg:"没有记录",
        beforePageText:"当前页",
        afterPageText:"共{0}页"
    });
    var grid = new Ext.grid.GridPanel({
        id:'grid.info',
        height:setHeight(),
        width:setWidth(),
        loadMask:{msg:'正在加载数据，请稍后...'},
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
        tbar:['起始:',{
            id : 'startDate.tb.info',
            xtype : 'datefield',
            name : 'startDate',
            emptyText : '点击输入日期',
            format : 'Y-m-d'
        }, {
            xtype : 'tbseparator'
        },'结束:',{
            id : 'endDate.tb.info',
            xtype : 'datefield',
            name : 'endDate',
            emptyText : '点击输入日期',
            format : 'Y-m-d'
        }, {
            xtype : 'tbseparator'
        },'用户名:',{
            id:'userName.tb.info',
            xtype:'textfield',
            emptyText:'请输入用户名',
            width : 100
        },{
            xtype : 'tbseparator'
        },'类型:',{
            id : 'status.tb.info',
            xtype : 'combo',
            store : new Ext.data.ArrayStore({
                autoDestroy : true,
                fields : ['value', 'key'],
                data : [
                    ['0', '未通过审核'],
                    ['1', '通过审核'],
                    ['2', '非法注册']
                ]
            }),
            valueField : 'value',
            displayField : 'key',
            mode : 'local',
            forceSelection : true,
            triggerAction : 'all',
            emptyText : '--请选择--',
            value : '',
            selectOnFocus : true,
            width : 100
        }, {
            xtype : 'tbseparator'
        }, {
            text : '查询',
            iconCls:'query',
            listeners : {
                click : function() {
                    var status = Ext.fly('status.tb.info').dom.value == '--请选择--'
                        ? null
                        : Ext.getCmp('status.tb.info').getValue();
                    var startDate = Ext.fly("startDate.tb.info").dom.value == '点击输入日期'
                        ? null
                        : Ext.fly('startDate.tb.info').dom.value;
                    var endDate = Ext.fly('endDate.tb.info').dom.value == '点击输入日期'
                        ? null
                        : Ext.fly('endDate.tb.info').dom.value;
                    var userName = Ext.fly('userName.tb.info').dom.value == '请输入用户名'
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
            text:'新增',
            iconCls:'add',
            handler:function(){
                insert_win(grid,store);
            }
        },{
            xtype : 'tbseparator'*/
        },{
            id:'btnremove.info',
            text:'删除',
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
            groupingTextTpl:'{text}({[values.rs.length]}条记录)'
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
        return '<font color="red">未通过审核</font>';
    } else if(value=='1') {
        return '<font color="green">通过审核</font>';
    } else {
        return '<font color="yellow">非法</font>';
    }
}

function modified_showUrl(value) {
    if(value.length==0||value==undefined) {
        return '<font color="red">未通过审核</font>';
    } else {
        return value;
    }
}

function flag_showUrl(value) {
    if(value=='0'){
        return '<a href="javascript:;" onclick="query_detail();" >详细</a>&nbsp;&nbsp;' +
            '<a href="javascript:;" onclick="update_win();" >修改</a>&nbsp;&nbsp;' +
            '<a href="javascript:;" onclick="allow();" >通过</a>';
    } else if(value=='1') {
        return '<a href="javascript:;" onclick="query_detail();" >详细</a>&nbsp;&nbsp;' +
            '<a href="javascript:;" onclick="update_win();" >修改</a>&nbsp;&nbsp;' +
            '<font color="gray">通过</font>';
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
                    blankText:'该项不能为空!'
                },
                items : [{
                    fieldLabel:"申请人",
                    value:item.data.userName
                },{
                    fieldLabel:"申请人身份证号",
                    value:item.data.userId
                },{
                    fieldLabel:"申请人Email",
                    value:item.data.email
                },{
                    fieldLabel:"申请人电话",
                    value:item.data.phone
                },{
                    fieldLabel:"申请人手机",
                    value:item.data.mobilePhone
                },{
                    fieldLabel:"申请地区",
                    value:item.data.districtName
                },{
                    fieldLabel:"申请单位",
                    value:item.data.orgName
                },{
                    fieldLabel:"申请人所在平台编号",
                    value:item.data.systemId
                },{
                    fieldLabel:"申请角色",
                    value:item.data.roleName
                },{
                    fieldLabel:"申请时间",
                    value:item.data.registerTime
                },{
                    fieldLabel:"审核通过时间",
                    value:modified_showUrl(item.data.modifiedTime)
                }]
            });
        });
    }
    var win = new Ext.Window({
        title:"详细信息-申请人注册信息",
        width:400,
        height:360,
        layout:'fit',
        modal:true,
        items: [formPanel],
        bbar:[
            new Ext.Toolbar.Fill(),
            new Ext.Button ({
                text : '保存',
                allowDepress : false,
                handler : function() {win.close();}
            }),
            new Ext.Button ({
                allowDepress : false,
                text : '关闭',
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
                title:'信息',
                msg:'<font color="green">确定要通过'+userName+'的申请？</font>',
                width:250,
                buttons:{'ok':'确定','no':'取消'},
                icon:Ext.MessageBox.WARNING,
                closable:false,
                fn:function(e){
                    if(e == 'ok'){
                        var myMask = new Ext.LoadMask(Ext.getBody(),{
                            msg:'正在处理,请稍后...',
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
                                    title:'信息',
                                    msg:msg,
                                    animEl:'btnRemove.db.external.info',
                                    buttons:{'ok':'确定'},
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
            title:'信息',
            msg:'<font color="green">您没有勾选任何记录!</font>',
            animEl:'export.info',
            buttons:{'ok':'确定'},
            icon:Ext.MessageBox.INFO,
            closable:false
        });
    }else if(count > 0){
        Ext.MessageBox.show({
            title:'信息',
            msg:'<font color="green">确定要删除所选记录？</font>',
            animEl:'export.info',
            buttons:{'ok':'确定','no':'取消'},
            icon:Ext.MessageBox.INFO,
            closable:false,
            fn:function(e){
                if(e == 'ok'){
                    var myMask = new Ext.LoadMask(Ext.getBody(),{
                        msg:'正在删除,请稍后...',
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
                                title:'信息',
                                msg:json.msg,
                                animEl:'export.info',
                                buttons:{'ok':'确定'},
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
                    blankText:'该项不能为空!'
                },
                items : [{
                    name:'account.id',
                    xtype:'hidden',
                    value:item.data.id
                },{
                    fieldLabel:'申请人',
                    xtype:'displayfield',
                    value:item.data.userName
                },{
                    name:'account.userName',
                    xtype:'hidden',
                    value:item.data.userName
                },{
                    fieldLabel:"申请人身份证号",
                    name:'account.userId',
                    value:item.data.userId
                },{
                    fieldLabel:"申请人Email",
                    name:'account.email',
                    value:item.data.email,
                    regex:/^\w+[\w.]*@[\w.]+\.\w+$/,
                    regexText:'请输入(例:hello@hzih.com)',
                    emptyText:'请输入(例:hello@hzih.com)'
                },{
                    fieldLabel:"申请人手机",
                    name:'account.mobilePhone',
                    value:item.data.mobilePhone,
                    regex:/^.{11}$/,
                    regexText:'请输入(例:13838168168)',
                    emptyText:'请输入(例:13838168168)'
                },{
                    fieldLabel:"申请人电话",
                    name:'account.phone',
                    value:item.data.phone,
                    regex:/^.{2,100}$/,
                    regexText:'请输入(例:0571-88880571)',
                    emptyText:'请输入(例:0571-88880571)'
                },{
                    fieldLabel:"申请地区",
                    xtype:'displayfield',
                    value:item.data.districtName
                },{
                    name:'account.districtCode',
                    xtype:'hidden',
                    value:item.data.districtCode
                },{
                    fieldLabel:"申请人单位",
                    xtype:'displayfield',
                    value:item.data.orgName
                },{
                    name:'account.orgCode',
                    xtype:'hidden',
                    value:item.data.orgCode
                },{
                    fieldLabel:"申请人所在平台编号",
                    name:'account.systemId',
                    value:item.data.systemId,
                    regex:/^.{8}$/,
                    regexText:'请输入您所在平台编号,在自动出现的字符后面填上2位',
                    emptyText:'请输入8位的您所在平台编号'
                },{
                    name:'account.status',
                    xtype:'hidden',
                    value:item.data.status
                },{
                    id:'roleId.info',
                    fieldLabel:"申请角色",hiddenName:'role.id',value:item.data.roleId,
                    xtype:'combo',
                    mode:'local',
                    emptyText :'--请选择--',
                    editable : false,
                    typeAhead:true,
                    forceSelection: true,
                    triggerAction:'all',
                    displayField:"key",valueField:"value",
                    store:storeRole
                },{
                    fieldLabel:"申请时间",
                    xtype:'displayfield',
                    value:item.data.registerTime
                },{
                    fieldLabel:"审核通过时间",
                    xtype:'displayfield',
                    value:modified_showUrl(item.data.modifiedTime)
                }]
            });
        });
    }
    var win = new Ext.Window({
        title:"修改信息",
        width:400,
        layout:'fit',
        height:360,
        modal:true,
        items:[formPanel],
        bbar:['->',{
            id:'update_win.info',
            text:'修改',
            handler:function(){
                Ext.MessageBox.show({
                    title:'信息',
                    msg:'<font color="green">是否修改角色信息?</font>',
                    width:250,
                    animEl:'update_win.info',
                    buttons:{'ok':'继续','no':'取消'},
                    icon:Ext.MessageBox.WARNING,
                    closable:false,
                    fn:function(e){
                        if(e=='ok'){
                            if (formPanel.form.isValid()) {
                                formPanel.getForm().submit({
                                    url :'RegisterAction_update.action',
                                    method :'POST',
                                    waitTitle :'系统提示',
                                    waitMsg :'正在修改,请稍后...',
                                    success : function(form,action) {
                                        var msg = action.result.msg;
                                        Ext.MessageBox.show({
                                            title:'信息',
                                            msg:msg,
                                            animEl:'update_win.info',
                                            width:300,
                                            buttons:{'ok':'确定','no':'取消'},
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
                                    title:'信息',
                                    msg:'修改失败,请填写完成再保存!',
                                    animEl:'update_win.info',
                                    width:300,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.ERROR,
                                    closable:false
                                });
                            }
                        }
                    }
                });
            }
        },{
            text:'关闭',
            handler:function(){
                win.close();
            }
        }
        ]
    }).show();
}
