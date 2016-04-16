Ext.onReady(function() {
    Ext.BLANK_IMAGE_URL = '../../ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';

    var csm = new Ext.grid.CheckboxSelectionModel();
    var rowNumber = new Ext.grid.RowNumberer();         //自动 编号
    var cm = new Ext.grid.ColumnModel([
        csm,
        rowNumber,
        /*{
            header : '信息编号',
            dataIndex : 'nid',
//            hidden:true,
            width:150
        },*/ {
            header : "主题",
            dataIndex : 'title',
            width:200
        }, {
            header : "信息内容",
            dataIndex : 'content',
            width:200
        }, {
            header : "文件",
            dataIndex : 'files',
            hidden:true,
            width:200
        }, {
            header : "发布时间",
            dataIndex : 'time',
            width:120
        }, {
            header : "类型",
            dataIndex : 'classname',
            width:200
        },{
            header:"操作",
            dataIndex:"button",
            renderer:xxbutton,
            width:80
        }
    ]);
    cm.defaultSortable = true;
    function xxbutton() {
        var returnStr = "&nbsp;&nbsp;&nbsp;<a href='javascript:;' onclick='lookmodel()'  >详细</a>&nbsp;&nbsp;&nbsp;<a href='javascript:;' onclick='updmodel()'>修改</a>";
        return returnStr;

    }
    var pageSize = gridPageSize();
    var ds = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            url : '../../NewsAction_findNews.action'
        }),//调用的动作
        reader : new Ext.data.JsonReader({
            totalProperty : 'newslist',
            root : 'newsrow'
        }, [ {
            name : 'nid',
            mapping : 'nid',
            type : 'int'
        }, {
            name : 'title',
            mapping : 'title',
            type : 'string'
        }, {
            name : 'content',
            mapping : 'content',
            type : 'string'
        }, {
            name : 'files',
            mapping : 'files',
            type : 'string'
        }, {
            name : 'time',
            mapping : 'time',
            type : 'string'
        }, {
            name : 'classname',
            mapping : 'classname',
            type : 'string'
        }//列的映射
        ])
    });
    ds.load({params:{start:0,limit:pageSize}});

    var grid;
    grid = new Ext.grid.GridPanel({
        // var grid = new Ext.grid.EditorGridPanel( {
        /* collapsible : true,// 是否可以展开
         animCollapse : false,// 展开时是否有动画效果*/
        id : 'resourcegrid',
//        title : '信息发布',
        border:false,
        store : ds,
        cm : cm,
        sm:csm,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        renderTo :Ext.getBody(), /*'noteDiv',*/
        // 添加分页工具栏
        bbar : new Ext.PagingToolbar({
            pageSize : pageSize,
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
        tbar : [ {
            id : 'New1',
            text : ' 添加  ',
            tooltip : '添加一个角色',
            iconCls : 'add',
            handler : function() {
                createMyform();
                winopen(myform);
            }
        }, {
            text : '删除',
            tooltip : '删除被选择的内容',
            iconCls : 'remove',
            handler : function() {
                var ids = null;
                var gs = grid.getSelectionModel().getSelections();
                if(gs.length==0) {
                    Ext.MessageBox.alert('信息提示',"请至少选择一条记录进行删除");
                    return;
                }
                for(var i = 0; i < gs.length; i++){
                    ids += gs[i].get("nid");
                    if(i < gs.length-1) {
                        ids += ",";
                    }
                }
//                Ext.Msg.alert('提示',ids);
                Ext.MessageBox.confirm('提示', '是否确定删除这'+gs.length+'条记录', callBack);
                function callBack(qrid) {
                    if("yes"==qrid){
                        Ext.Ajax.request({
                            url:'../../NewsAction_delNews.action',
                            success:function(response,result){
                                var reText = Ext.util.JSON.decode(response.responseText);
                                Ext.Msg.alert('提示',reText.msg);
                                grid.render();
                                ds.reload();
                            },
                            params:{ids:ids}
                        });
                    }
                }
            }
        }],
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
                    fieldLabel : '主题' ,
                    name : 'title',
                    id:'title',
                    width:163,
                    xtype: 'displayfield'
                },{
                    xtype : 'textarea',
                    grow : false,
                    anchor    : '90% 70%',
                    maxLength : 10000,
                    minLength : 1,
                    fieldLabel : '信息内容' ,
                    name : 'content',
                    id: 'content',
                    width:163,
                    readOnly:true
                }/*,{
                 fieldLabel : '文件' ,
                 name : 'files',
                 id: 'files',
                 width:163,
                 xtype: 'displayfield'
                 }*/,{
                    fieldLabel : '发布时间' ,
                    name : 'time',
                    id: 'time',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '类型' ,
                    name : 'classname',
                    id: 'classname',
                    width:163,
                    xtype: 'displayfield'
                }
            ]
        });
    }
    Model.lookxx = function lookxx(){
        createLookform();
        Ext.getCmp("title").setValue(grid.getSelectionModel().getSelections()[0].get("title"));
        Ext.getCmp("content").setValue(grid.getSelectionModel().getSelections()[0].get("content"));
//        Ext.getCmp("files").setValue(grid.getSelectionModel().getSelections()[0].get("files"));
        Ext.getCmp("time").setValue(grid.getSelectionModel().getSelections()[0].get("time"));
        Ext.getCmp("classname").setValue(grid.getSelectionModel().getSelections()[0].get("classname"));
        winlookxx(myform);
    }
    var win = null;
    function winlookxx(form) {
        var myform = form;
        win = new Ext.Window({
            title : '查看信息',
            width : 600,
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

    function createMyform() {
        myform = new Ext.form.FormPanel({
            labelWidth:80,
            //renderTo : "formt",
            frame : true ,
            defaultType : 'textfield' ,
            buttonAlign : 'right' ,
            labelAlign : 'right' ,
            //此处添加url，那么在getForm().sumit方法不需要在添加了url地址了
            url:'../../NewsAction_addNews.action',
            baseParams : {create : true },
            //  labelWidth : 70 ,
            defaults:{
//                allowBlank: false,
                blankText: '不能为空!',
                msgTarget: 'side'
            },
            items : [
                {
                    fieldLabel : '主题' ,
                    name : 'title',
                    width:431,
                    regex:/^\S{1,60}$/,
                    allowBlank: false,
                    emptyText: '请输入主题(1-30字)'
                },{
                    xtype : 'textarea',
                    grow : false,
                    anchor    : '90% 70%',
                    maxLength : 10000,
                    minLength : 1,
//                    preventScrollbars : true,
                    fieldLabel : '信息内容' ,
                    name : 'content',
                    allowBlank: false,
                    emptyText: '请输入信息内容(1-5000字)'
                }/*,{
                 fieldLabel : '文件' ,
                 name : 'files',
                 width:163
                 //                    regex:/^\S{1,30}$/
                 }*/,{
                    fieldLabel : '类型' ,
                    name : 'classname',
                    width:163,
                    regex:/^\S{1,50}$/,
                    allowBlank: false,
                    emptyText: '请输入信息类型(1-25字)'
                }
            ]
        });
    }
    function winopen(form) {
        var myform = form;
        win = new Ext.Window({
            width : 600,
            title:"增加新信息",
            items: [myform],
            bbar : ["->",
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

    Model.updxx = function updxx() {
        createUpdform();
        Ext.getCmp("nid").setValue(grid.getSelectionModel().getSelections()[0].get("nid"));
        Ext.getCmp("title").setValue(grid.getSelectionModel().getSelections()[0].get("title"));
        Ext.getCmp("content").setValue(grid.getSelectionModel().getSelections()[0].get("content"));
//        Ext.getCmp("files").setValue(grid.getSelectionModel().getSelections()[0].get("files"));
        Ext.getCmp("classname").setValue(grid.getSelectionModel().getSelections()[0].get("classname"));
        winupd(myform);
    }
    function createUpdform() {
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
                    xtype :'hidden',
                    name:'nid',
                    id:'nid'
                },{
                    fieldLabel : '主题' ,
                    id:'title',
                    name : 'title',
                    width:163,
                    regex:/^\S{1,60}$/,
                    allowBlank: false,
                    emptyText: '请输入主题(1-30字)'
                },{
                    xtype : 'textarea',
                    grow : false,
                    anchor    : '90% 70%',
                    maxLength : 10000,
                    minLength : 1,
                    fieldLabel : '信息内容' ,
                    name : 'content',
                    id: 'content',
                    allowBlank: false,
                    emptyText: '请输入信息内容(1-5000字)'
                }/*,{
                 fieldLabel : '文件' ,
                 name : 'files',
                 id: 'files',
                 width:163
                 }*/,{
                    fieldLabel : '类型' ,
                    name : 'classname',
                    id: 'classname',
                    width:163,
                    regex:/^\S{1,50}$/,
                    allowBlank: false,
                    emptyText: '请输入信息类型(1-25字)'
                }
            ]
        });
    }
    function winupd(form) {
        var myform = form;
        win = new Ext.Window({
            width : 600,
            title:"修改信息",
            items: [myform],
            bbar : ["->",
                {
                    text : '确定',
                    handler : function(){
                        //FormPanel自身带异步提交方式
                        if(myform.getForm().isValid()) {
                            myform.getForm().submit({
                                url: '../../NewsAction_updNews.action',
                                waitTitle : '请等待' ,
                                waitMsg: '正在提交中',
                                success:function(form,action) {
                                    var msg = action.result.msg;
                                    Ext.Msg.alert('提示',msg);
                                    grid.render();
                                    ds.reload();
                                    win.close();
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
function updmodel() {
    Model.updxx();
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


