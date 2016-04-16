Ext.onReady(function() {
    Ext.BLANK_IMAGE_URL = '../../ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';

    var csm = new Ext.grid.CheckboxSelectionModel();
    var rowNumber = new Ext.grid.RowNumberer();         //�Զ� ���
    var cm = new Ext.grid.ColumnModel([
        csm,
        rowNumber,
        /*{
            header : '��Ϣ���',
            dataIndex : 'nid',
//            hidden:true,
            width:150
        },*/ {
            header : "����",
            dataIndex : 'title',
            width:200
        }, {
            header : "��Ϣ����",
            dataIndex : 'content',
            width:200
        }, {
            header : "�ļ�",
            dataIndex : 'files',
            hidden:true,
            width:200
        }, {
            header : "����ʱ��",
            dataIndex : 'time',
            width:120
        }, {
            header : "����",
            dataIndex : 'classname',
            width:200
        },{
            header:"����",
            dataIndex:"button",
            renderer:xxbutton,
            width:80
        }
    ]);
    cm.defaultSortable = true;
    function xxbutton() {
        var returnStr = "&nbsp;&nbsp;&nbsp;<a href='javascript:;' onclick='lookmodel()'  >��ϸ</a>&nbsp;&nbsp;&nbsp;<a href='javascript:;' onclick='updmodel()'>�޸�</a>";
        return returnStr;

    }
    var pageSize = gridPageSize();
    var ds = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            url : '../../NewsAction_findNews.action'
        }),//���õĶ���
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
        }//�е�ӳ��
        ])
    });
    ds.load({params:{start:0,limit:pageSize}});

    var grid;
    grid = new Ext.grid.GridPanel({
        // var grid = new Ext.grid.EditorGridPanel( {
        /* collapsible : true,// �Ƿ����չ��
         animCollapse : false,// չ��ʱ�Ƿ��ж���Ч��*/
        id : 'resourcegrid',
//        title : '��Ϣ����',
        border:false,
        store : ds,
        cm : cm,
        sm:csm,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        renderTo :Ext.getBody(), /*'noteDiv',*/
        // ��ӷ�ҳ������
        bbar : new Ext.PagingToolbar({
            pageSize : pageSize,
            store : ds,
            displayInfo:true,
            displayMsg:"��ʾ��{0}����¼����{1}����¼��һ��{2}��",
            emptyMsg:"û�м�¼",
            beforePageText:"��ǰҳ",
            afterPageText:"��{0}ҳ"
        }),
        // ������ݵĹ�����
        viewConfig:{
            autoFill:true
            //forceFit:true
        },
        tbar : [ {
            id : 'New1',
            text : ' ���  ',
            tooltip : '���һ����ɫ',
            iconCls : 'add',
            handler : function() {
                createMyform();
                winopen(myform);
            }
        }, {
            text : 'ɾ��',
            tooltip : 'ɾ����ѡ�������',
            iconCls : 'remove',
            handler : function() {
                var ids = null;
                var gs = grid.getSelectionModel().getSelections();
                if(gs.length==0) {
                    Ext.MessageBox.alert('��Ϣ��ʾ',"������ѡ��һ����¼����ɾ��");
                    return;
                }
                for(var i = 0; i < gs.length; i++){
                    ids += gs[i].get("nid");
                    if(i < gs.length-1) {
                        ids += ",";
                    }
                }
//                Ext.Msg.alert('��ʾ',ids);
                Ext.MessageBox.confirm('��ʾ', '�Ƿ�ȷ��ɾ����'+gs.length+'����¼', callBack);
                function callBack(qrid) {
                    if("yes"==qrid){
                        Ext.Ajax.request({
                            url:'../../NewsAction_delNews.action',
                            success:function(response,result){
                                var reText = Ext.util.JSON.decode(response.responseText);
                                Ext.Msg.alert('��ʾ',reText.msg);
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
        loadMask : true,// �������ֶ���
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
                    fieldLabel : '����' ,
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
                    fieldLabel : '��Ϣ����' ,
                    name : 'content',
                    id: 'content',
                    width:163,
                    readOnly:true
                }/*,{
                 fieldLabel : '�ļ�' ,
                 name : 'files',
                 id: 'files',
                 width:163,
                 xtype: 'displayfield'
                 }*/,{
                    fieldLabel : '����ʱ��' ,
                    name : 'time',
                    id: 'time',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '����' ,
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
            title : '�鿴��Ϣ',
            width : 600,
//            height :490,
            items: [myform],
            bbar : ["->",
                {
                    text:'�ر�',
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
            //�˴����url����ô��getForm().sumit��������Ҫ�������url��ַ��
            url:'../../NewsAction_addNews.action',
            baseParams : {create : true },
            //  labelWidth : 70 ,
            defaults:{
//                allowBlank: false,
                blankText: '����Ϊ��!',
                msgTarget: 'side'
            },
            items : [
                {
                    fieldLabel : '����' ,
                    name : 'title',
                    width:431,
                    regex:/^\S{1,60}$/,
                    allowBlank: false,
                    emptyText: '����������(1-30��)'
                },{
                    xtype : 'textarea',
                    grow : false,
                    anchor    : '90% 70%',
                    maxLength : 10000,
                    minLength : 1,
//                    preventScrollbars : true,
                    fieldLabel : '��Ϣ����' ,
                    name : 'content',
                    allowBlank: false,
                    emptyText: '��������Ϣ����(1-5000��)'
                }/*,{
                 fieldLabel : '�ļ�' ,
                 name : 'files',
                 width:163
                 //                    regex:/^\S{1,30}$/
                 }*/,{
                    fieldLabel : '����' ,
                    name : 'classname',
                    width:163,
                    regex:/^\S{1,50}$/,
                    allowBlank: false,
                    emptyText: '��������Ϣ����(1-25��)'
                }
            ]
        });
    }
    function winopen(form) {
        var myform = form;
        win = new Ext.Window({
            width : 600,
            title:"��������Ϣ",
            items: [myform],
            bbar : ["->",
                {
                    text : 'ȷ��',
                    handler : function(){
                        //FormPanel������첽�ύ��ʽ
                        if(myform.getForm().isValid()) {
                            myform.getForm().submit({
//                            url: '../../RoleManageAction_addRoleManage.action',
                                waitTitle : '��ȴ�' ,
                                waitMsg: '�����ύ��',
                                success:function(form,action) {
                                    var msg = action.result.msg;
                                    if(msg=="ssadd"){
                                        Ext.Msg.alert('��ʾ','��ɫ�����ظ�');
                                    }else{
                                        Ext.Msg.alert('��ʾ',msg);
                                        grid.render();
                                        ds.reload();
                                        win.close();
                                    }
                                }
                            });
                        }else {
                            Ext.Msg.alert('��ʾ','������д����ȷ��Ϣ');
                        }
                    }
                },{
                    text:'�ر�',
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
                    fieldLabel : '����' ,
                    id:'title',
                    name : 'title',
                    width:163,
                    regex:/^\S{1,60}$/,
                    allowBlank: false,
                    emptyText: '����������(1-30��)'
                },{
                    xtype : 'textarea',
                    grow : false,
                    anchor    : '90% 70%',
                    maxLength : 10000,
                    minLength : 1,
                    fieldLabel : '��Ϣ����' ,
                    name : 'content',
                    id: 'content',
                    allowBlank: false,
                    emptyText: '��������Ϣ����(1-5000��)'
                }/*,{
                 fieldLabel : '�ļ�' ,
                 name : 'files',
                 id: 'files',
                 width:163
                 }*/,{
                    fieldLabel : '����' ,
                    name : 'classname',
                    id: 'classname',
                    width:163,
                    regex:/^\S{1,50}$/,
                    allowBlank: false,
                    emptyText: '��������Ϣ����(1-25��)'
                }
            ]
        });
    }
    function winupd(form) {
        var myform = form;
        win = new Ext.Window({
            width : 600,
            title:"�޸���Ϣ",
            items: [myform],
            bbar : ["->",
                {
                    text : 'ȷ��',
                    handler : function(){
                        //FormPanel������첽�ύ��ʽ
                        if(myform.getForm().isValid()) {
                            myform.getForm().submit({
                                url: '../../NewsAction_updNews.action',
                                waitTitle : '��ȴ�' ,
                                waitMsg: '�����ύ��',
                                success:function(form,action) {
                                    var msg = action.result.msg;
                                    Ext.Msg.alert('��ʾ',msg);
                                    grid.render();
                                    ds.reload();
                                    win.close();
                                }
                            });
                        }else {
                            Ext.Msg.alert('��ʾ','������д����ȷ��Ϣ');
                        }
                    }
                },{
                    text:'�ر�',
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


