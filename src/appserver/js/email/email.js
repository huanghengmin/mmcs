Ext.onReady(function() {
    Ext.BLANK_IMAGE_URL = '../../ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';

    var pagesize = gridPageSize();
    var csm = new Ext.grid.CheckboxSelectionModel({
    });
     var rowNumber = new Ext.grid.RowNumberer();         //自动 编号
    var cm = new Ext.grid.ColumnModel([
//        csm,
        rowNumber,
        /*{
            header : 'ID',
            dataIndex : 'id',
            hidden:true,
            width:100
        }, {
            header : "消息内容",
            dataIndex : 'msgName',
            width:150
        },*/{
            header : "收件人姓名",
            dataIndex : 'username',
            width:150
        }, {
            header : "消息类型",
            dataIndex : 'msgType',
            width:150
        },  {
            header : "收件人单位",
            dataIndex : 'unit',
            width:150
        }, {
            header : "收件人角色",
            dataIndex : 'roleids',
            width:150
        }, {
            header : "是否已阅读",
            dataIndex : 'ifchecked',
            width:120,
            renderer:function (value){
                if(value=="是"){
                    return "<font style='color:green;'>是</font>";
                }else if(value=="否"){
                    return "<font style='color:red;'>否</font>";
                }
            }
        }, {
            header : "消息发送时间",
            dataIndex : 'sendTime',
            width:200
        },{
            header:"操作",
            dataIndex:"button",
            renderer:xxbutton,
            width:100
        }
    ]);
    cm.defaultSortable = true;
    function xxbutton() {
        var returnStr = "<a href='javascript:;' style='color:blue;' onclick='lookmodel()'>详细</a>";
        return returnStr;

    }

    var ds = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            url : '../../SysmessageAction_findMessageGet.action'
        }),//调用的动作
        reader : new Ext.data.JsonReader({
            totalProperty : 'mulist',
            root : 'murow'
        }, [ {
            name : 'id',
            mapping : 'id',
            type : 'int'
        }, {
            name : 'msgName',
            mapping : 'msgName',
            type : 'string'
        }, {
            name : 'msgType',
            mapping : 'msgType',
            type : 'string'
        }, {
            name : 'username',
            mapping : 'username',
            type : 'string'
        }, {
            name : 'unit',
            mapping : 'unit',
            type : 'string'
        }, {
            name : 'roleids',
            mapping : 'roleids',
            type : 'string'
        }, {
            name : 'ifchecked',
            mapping : 'ifchecked',
            type : 'string'
        }, {
            name : 'sendTime',
            mapping : 'sendTime',
            type : 'string'
        }//列的映射
        ])
    });
    ds.load({params:{start:0,limit:pagesize}});

    var grid;
    grid = new Ext.grid.GridPanel({
        // var grid = new Ext.grid.EditorGridPanel( {
        /* collapsible : true,// 是否可以展开
         animCollapse : false,// 展开时是否有动画效果*/
        id : 'resourcegrid',
//        title : '收件箱',
        border:false,
        store : ds,
        cm : cm,
//        sm:csm,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        renderTo :Ext.getBody(), /*'noteDiv',*/
        /*
         * // 添加内陷的按钮 buttons : [ { text : '保存' }, { text : '取消' }],
         * buttonAlign : 'center',// 按钮对齐
         *
         */
        // 添加分页工具栏
        bbar : new Ext.PagingToolbar({
            pageSize : pagesize,
            store : ds,
            displayInfo:true,
            displayMsg:"显示第{0}条记录到第{1}条记录，一共{2}条",
            emptyMsg:"没有记录",
            beforePageText:"当前页",
            afterPageText:"共{0}页"
        }),
        // 添加内陷的工具条
        viewConfig:{
            autoFill:true
            //forceFit:true
        },
        width : setWidth(),
        height :setHeight(),
//        frame : true,
        loadMask : true,// 载入遮罩动画
        autoShow : true
    });
    new Ext.Viewport({
        layout:'fit',
        renderTo: Ext.getBody(),
        items:[grid]
    });
    var myform;
    function createLookform() {
        myform = new Ext.form.FormPanel({
            labelWidth:80,
            //renderTo : "formt",
            frame : true ,
            defaultType : 'textfield' ,
            buttonAlign : 'right' ,
            labelAlign : 'right' ,
            baseParams : {create : true },
            //  labelWidth : 70 ,
            items : [
                {
                    xtype : 'textarea',
                    grow : false,
                    anchor    : '90% 45%',
                    maxLength : 200,
                    minLength : 1,
//                    preventScrollbars : true,
                    fieldLabel : '消息内容' ,
                    emptyText: '请填写消息内容(1-100字)',
                    name : 'msgName',
                    id: 'msgName',
                    readOnly: true
                },{
                    fieldLabel : '消息类型' ,
                    name : 'msgType',
                    id: 'msgType',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '收件人姓名' ,
                    name : 'username',
                    id: 'username',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '收件人单位' ,
                    name : 'unit',
                    id: 'unit',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '收件人角色' ,
                    name : 'roleids',
                    id: 'roleids',
                    width:163,
                    xtype: 'displayfield'
                },/*{
                    fieldLabel : '是否已阅读' ,
                    name : 'ifchecked',
                    id: 'ifchecked',
                    width:163,
                    xtype: 'displayfield'
                },*/{
                    fieldLabel : '消息发送时间' ,
                    name : 'sendTime',
                    id: 'sendTime',
                    width:163,
                    xtype: 'displayfield'
                }
            ]
        });
    }
    Model.lookxx = function lookxx(){
        createLookform();
        Ext.getCmp("msgName").setValue(grid.getSelectionModel().getSelections()[0].get("msgName"));
        Ext.getCmp("msgType").setValue(grid.getSelectionModel().getSelections()[0].get("msgType"));
        Ext.getCmp("username").setValue(grid.getSelectionModel().getSelections()[0].get("username"));
        Ext.getCmp("unit").setValue(grid.getSelectionModel().getSelections()[0].get("unit"));
        Ext.getCmp("roleids").setValue(grid.getSelectionModel().getSelections()[0].get("roleids"));
//        Ext.getCmp("ifchecked").setValue(grid.getSelectionModel().getSelections()[0].get("ifchecked"));
//        if(grid.getSelectionModel().getSelections()[0].get("ifchecked")=="是"){
//            Ext.getCmp("ifchecked").setValue("<font style='color:green;'>是</font>");
//        }else {
//            Ext.getCmp("ifchecked").setValue("<font style='color:red;'>否</font>");
//        }
        Ext.getCmp("sendTime").setValue(grid.getSelectionModel().getSelections()[0].get("sendTime"));
        winlookxx(myform);
        if(grid.getSelectionModel().getSelections()[0].get("ifchecked")=="否"){
            Ext.Ajax.request({
                url:'../../SysmessageAction_updSysmessage.action',
                success:function(response,result){
                    var reText = Ext.util.JSON.decode(response.responseText);
//                    Ext.Msg.alert('提示',reText.msg);
                    grid.render();
                    ds.reload();
                },
                params:{id:grid.getSelectionModel().getSelections()[0].get("id"),ifchecked:"1"}
            });
        }
    }
    function winlookxx(form) {
        var myform = form;
        win = new Ext.Window({
            title : '查看消息',
            width : 500,
//            height :490,
            items: [myform],
            bbar : ["->",
                {
                    text:'关闭',
                    handler:function() {
                        win.close();
                    }
                }
            ]
        });
        win.show();
    }
    var userJsonStore = new Ext.data.JsonStore({
        fields : [ "id", "username" ],
        url : '../../SysmessageAction_findUsersJson.action',
        autoLoad : true,
        root : "rows"
    });
    function createMyform() {
        myform = new Ext.form.FormPanel({
            labelWidth:80,
            //renderTo : "formt",
            frame : true ,
            defaultType : 'textfield' ,
            buttonAlign : 'right' ,
            labelAlign : 'right' ,
            //此处添加url，那么在getForm().sumit方法不需要在添加了url地址了
            url:'../../SysmessageAction_addSysMessage.action',
            baseParams : {create : true },
            //  labelWidth : 70 ,
            defaults:{
//                allowBlank: false,
                blankText: '不能为空!',
                msgTarget: 'side'
            },
            items : [
                {
                    xtype : 'textarea',
                    grow : false,
                    anchor    : '90%',
                    maxLength : 200,
                    minLength : 1,
//                    preventScrollbars : true,
                    fieldLabel : '消息内容' ,
                    emptyText: '请填写消息内容(1-100字)',
                    name : 'msgName',
                    allowBlank: false
                }/*,{
                 fieldLabel : '文件' ,
                 name : 'files',
                 width:163
                 //                    regex:/^\S{1,30}$/
                 }*/,new Ext.form.ComboBox({
                    hiddenName:"msgType",
                    id:'select3',
                    fieldLabel:"消息类型",
                    emptyText: '请选择消息类型',
                    store : new Ext.data.SimpleStore({fields:['value','name'],data:[ [0,'审批通知类'], [1,'违规通知类']]}),
                    valueField : "value",
                    displayField : "name",
                    typeAhead : true,
                    mode : "local",
                    forceSelection : true,
                    triggerAction : "all",
                    OnFocus : true,
                    allowBlank: false
                }),new Ext.form.ComboBox({
                    hiddenName:'msgAdminId',
                    id:'msgAdminId3',
                    fieldLabel:"收件人",
                    emptyText: '请选择收件人',
                    store: userJsonStore,
                    valueField:"id",
                    displayField:"username",
                    typeAhead: true,
                    mode: "local",
                    forceSelection: true,
                    triggerAction: "all",
                    OnFocus:true ,
                    allowBlank: false
                })
            ]
        });
    }
    function winopen(form) {
        var myform = form;
        win = new Ext.Window({
            width : 400,
            items: [myform],
            title:"增加新消息",
            bbar : [ "->",
                {
                    text : '确定',
                    handler : function(){
                        //FormPanel自身带异步提交方式
                        if(myform.getForm().isValid()) {
                            myform.getForm().submit({
//                            url: '../../RoleManageAction_addRoleManage.action',
                                waitTitle : '请等待' ,
                                waitMsg: '正在提交中',
                                success:function(form,action) {
                                    var msg = action.result.msg;
                                    if(msg=="ssadd"){
                                        Ext.Msg.alert('提示','角色名称重复');
                                    }else{
                                        Ext.Msg.alert('提示',msg);
                                        grid.render();
                                        ds.reload();
                                        win.close();
                                    }
                                }

                            });
                        }else {
                            Ext.Msg.alert('提示','请先填写完正确信息');
                        }
                    }
                },{
                    text:'关闭',
                    handler:function() {
                        win.close();
                    }
                }
            ]
        });
        win.show();
    }
});

var Model = new Object;
function lookmodel(){
    Model.lookxx();
}

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


