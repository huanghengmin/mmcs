Ext.onReady(function() {
    Ext.BLANK_IMAGE_URL = '../../ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';

    var pagesize = gridPageSize();
    var csm = new Ext.grid.CheckboxSelectionModel({
    });
     var rowNumber = new Ext.grid.RowNumberer();         //�Զ� ���
    var cm = new Ext.grid.ColumnModel([
//        csm,
        rowNumber,
        /*{
            header : 'ID',
            dataIndex : 'id',
            hidden:true,
            width:100
        }, {
            header : "��Ϣ����",
            dataIndex : 'msgName',
            width:150
        },*/{
            header : "�ռ�������",
            dataIndex : 'username',
            width:150
        }, {
            header : "��Ϣ����",
            dataIndex : 'msgType',
            width:150
        },  {
            header : "�ռ��˵�λ",
            dataIndex : 'unit',
            width:150
        }, {
            header : "�ռ��˽�ɫ",
            dataIndex : 'roleids',
            width:150
        }, {
            header : "�Ƿ����Ķ�",
            dataIndex : 'ifchecked',
            width:120,
            renderer:function (value){
                if(value=="��"){
                    return "<font style='color:green;'>��</font>";
                }else if(value=="��"){
                    return "<font style='color:red;'>��</font>";
                }
            }
        }, {
            header : "��Ϣ����ʱ��",
            dataIndex : 'sendTime',
            width:200
        },{
            header:"����",
            dataIndex:"button",
            renderer:xxbutton,
            width:100
        }
    ]);
    cm.defaultSortable = true;
    function xxbutton() {
        var returnStr = "<a href='javascript:;' style='color:blue;' onclick='lookmodel()'>��ϸ</a>";
        return returnStr;

    }

    var ds = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
            url : '../../SysmessageAction_findMessageGet.action'
        }),//���õĶ���
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
        }//�е�ӳ��
        ])
    });
    ds.load({params:{start:0,limit:pagesize}});

    var grid;
    grid = new Ext.grid.GridPanel({
        // var grid = new Ext.grid.EditorGridPanel( {
        /* collapsible : true,// �Ƿ����չ��
         animCollapse : false,// չ��ʱ�Ƿ��ж���Ч��*/
        id : 'resourcegrid',
//        title : '�ռ���',
        border:false,
        store : ds,
        cm : cm,
//        sm:csm,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        renderTo :Ext.getBody(), /*'noteDiv',*/
        /*
         * // ������ݵİ�ť buttons : [ { text : '����' }, { text : 'ȡ��' }],
         * buttonAlign : 'center',// ��ť����
         *
         */
        // ��ӷ�ҳ������
        bbar : new Ext.PagingToolbar({
            pageSize : pagesize,
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
                    xtype : 'textarea',
                    grow : false,
                    anchor    : '90% 45%',
                    maxLength : 200,
                    minLength : 1,
//                    preventScrollbars : true,
                    fieldLabel : '��Ϣ����' ,
                    emptyText: '����д��Ϣ����(1-100��)',
                    name : 'msgName',
                    id: 'msgName',
                    readOnly: true
                },{
                    fieldLabel : '��Ϣ����' ,
                    name : 'msgType',
                    id: 'msgType',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '�ռ�������' ,
                    name : 'username',
                    id: 'username',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '�ռ��˵�λ' ,
                    name : 'unit',
                    id: 'unit',
                    width:163,
                    xtype: 'displayfield'
                },{
                    fieldLabel : '�ռ��˽�ɫ' ,
                    name : 'roleids',
                    id: 'roleids',
                    width:163,
                    xtype: 'displayfield'
                },/*{
                    fieldLabel : '�Ƿ����Ķ�' ,
                    name : 'ifchecked',
                    id: 'ifchecked',
                    width:163,
                    xtype: 'displayfield'
                },*/{
                    fieldLabel : '��Ϣ����ʱ��' ,
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
//        if(grid.getSelectionModel().getSelections()[0].get("ifchecked")=="��"){
//            Ext.getCmp("ifchecked").setValue("<font style='color:green;'>��</font>");
//        }else {
//            Ext.getCmp("ifchecked").setValue("<font style='color:red;'>��</font>");
//        }
        Ext.getCmp("sendTime").setValue(grid.getSelectionModel().getSelections()[0].get("sendTime"));
        winlookxx(myform);
        if(grid.getSelectionModel().getSelections()[0].get("ifchecked")=="��"){
            Ext.Ajax.request({
                url:'../../SysmessageAction_updSysmessage.action',
                success:function(response,result){
                    var reText = Ext.util.JSON.decode(response.responseText);
//                    Ext.Msg.alert('��ʾ',reText.msg);
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
            title : '�鿴��Ϣ',
            width : 500,
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
            //�˴����url����ô��getForm().sumit��������Ҫ�������url��ַ��
            url:'../../SysmessageAction_addSysMessage.action',
            baseParams : {create : true },
            //  labelWidth : 70 ,
            defaults:{
//                allowBlank: false,
                blankText: '����Ϊ��!',
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
                    fieldLabel : '��Ϣ����' ,
                    emptyText: '����д��Ϣ����(1-100��)',
                    name : 'msgName',
                    allowBlank: false
                }/*,{
                 fieldLabel : '�ļ�' ,
                 name : 'files',
                 width:163
                 //                    regex:/^\S{1,30}$/
                 }*/,new Ext.form.ComboBox({
                    hiddenName:"msgType",
                    id:'select3',
                    fieldLabel:"��Ϣ����",
                    emptyText: '��ѡ����Ϣ����',
                    store : new Ext.data.SimpleStore({fields:['value','name'],data:[ [0,'����֪ͨ��'], [1,'Υ��֪ͨ��']]}),
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
                    fieldLabel:"�ռ���",
                    emptyText: '��ѡ���ռ���',
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
            title:"��������Ϣ",
            bbar : [ "->",
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


