/*
 * Ext JS Library 2.0.2
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing��extjs.com
 *
 * extjs.com/license
 */

Ext.onReady(function(){
    Ext.BLANK_IMAGE_URL = '../../ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var treeLoader =  new Ext.tree.TreeLoader({
//        dataUrl:'../../SysAssessmentAction_findSysAssessment.action',
//        dataUrl:'../../SysAssessmentAction_findSingleAssessment.action',
//        dataUrl:'column_tree_data.js',
          uiProviders:{
            'col': Ext.tree.ColumnNodeUI
          }
    });

    //�������ĸ��ڵ�
    var root = new Ext.tree.AsyncTreeNode({
        id:"1",//���ڵ�id
        levelCode:'310000',
        draggable:false,
        expanded :true,
        text:"�����"
    });

    var tree = new Ext.tree.ColumnTree({
        el:'tree-ct',
//        layout:'fit',
        rootVisible:false,
        border:false,
        autoScroll:true,
        width:setWidth(),
        animate:true,//��������Ч��
//        title: '��������',
        columns:[
            {header: "����",align:'center', sortable: true, dataIndex: 'address',width:Ext.getBody().getWidth()/7+253},
            {header: "ע����Ϣ�÷�", align:'center', sortable: true, dataIndex: 'reg_score',width:Ext.getBody().getWidth()/7-50},
            {header: "Υ�洦���÷�",align:'center', sortable: true, dataIndex: 'abn_score',width:Ext.getBody().getWidth()/7-50},
            {header: "����Ա��½����÷�", align:'center', sortable: true, dataIndex: 'log_score',width:Ext.getBody().getWidth()/7-20},
            {header: "ϵͳѲ������÷�", align:'center', sortable: true,dataIndex: 'ins_score',width:Ext.getBody().getWidth()/7-30},
            {header: "ϵͳ��Ծ����÷�",align:'center', sortable: true,dataIndex: 'act_score',width:Ext.getBody().getWidth()/7-30} ,
            {header: "�ܷ�",align:'center', sortable: true,dataIndex: 'sum_score',width:Ext.getBody().getWidth()/7-90}/*,
            {header: "�������",width:200,align:'center', sortable: true,dataIndex: 'show_flag',renderer:show_flag*//*,width:Ext.getBody().getWidth()/7*//*}*/
        ],
        loader:treeLoader,
        root: root
        });

        //����
        tree.on('click', function(node, event){
            if(node.leaf){  //�����Ҷ�ڵ�
                show_system_score(node);
            }else{
                show_address_score(node);
            }
        });
    //˫��
//        tree.on('dblclick', function(node, event){
//            if(node.leaf){  //�����Ҷ�ڵ�
//                show_system_score(node);
//            }else{
//                show_address_score(node);
//            }
//        });

    //�����Ҽ�����¼�
    /*    tree.on('contextmenu', function(node, event) {// �����˵�����
     event.preventDefault();// ��ֹ�����Ĭ���Ҽ��˵���ʾ
     node.select(); //�ڵ�ѡ��
     if(node.leaf){
     alert("Ҷ�ӽ��!")
     } else{
     alert("��Ҷ�ӽ��!");
     }
     right_menu.showAt(event.getXY());// ȡ����������꣬չʾ�˵�
     });

     var right_menu= new Ext.menu.Menu({
     //�һ��¼�
     items:[{
     xtype:"button",
     text:"���ӽڵ�",
     iconCls:"add",
     pressed:true,
     handler : function(tree) {
     var selectedNode = tree.getSelectionModel().getSelectedNode();   //�˴�Ϊ��ѡ�еĽڵ��������ӽڵ�
     alert(selectedNode.attributes.address)
     }
     },{
     xtype:"button",
     text:"�޸Ľڵ�",
     iconCls:"replace",
     pressed:true,
     handler : function(tree) {
     onUpdate();
     }
     },{
     xtype:"button",
     text:"ɾ���ڵ�",
     iconCls:"delete",
     pressed:true,
     handler : function(tree) {
     onDeleteNode();
     }
     }]
     });*/


    tree.on('beforeload',
        function(node){
//          alert("beforeload");
//          tree.loader.dataUrl = 'data2.js'
            if(root==node){
                tree.loader.dataUrl = '../../SysAssessmentAction_findSingleAssessment.action'
            }else
            tree.loader.dataUrl = '../../SysAssessmentAction_findSysAssessment.action?levelCode='+node.attributes.levelCode
        });
    new Ext.Viewport({
        layout:'fit',
        renderTo: Ext.getBody(),
        items:[tree]
    });
    tree.render();
    root.expand();
});

    /*function onInsertNode(){
    alert(7);
    }

    function onUpdate(){
    alert(8);
    }

    function onDeleteNode(){
    alert(9);
    }

    function show_flag(){
    return String.format(
    '<font  id = "flag_delete" style="color:green;" onclick = "deleteSysAssessment()"><u>ɾ��</u></font> '+
    '<font  id = "flag_delete" style="color:green;" onclick = "updateSysAssessment()"><u>����</u></font> '
    );
    }

    function deleteSysAssessment(){
    alert('delete');
    }

    function updateSysAssessment(){
    alert('update');
    }*/

/*function show_address_score(node){
    var formPanel = new Ext.form.FormPanel({
        frame:true,
        width:500,
        autoScroll:true,
        baseCls : 'x-plain',
        labelWidth:150,
        labelAlign:'right',
        defaultWidth:300,
        layout:'form',
        border:false,
        defaults:{
            width:250
        },
        items:[
            new Ext.form.DisplayField({
                fieldLabel:'����',
                value:node.attributes.address
            }),
            new Ext.form.DisplayField({
                fieldLabel:'ע����Ϣ�÷�',
                value:node.attributes.reg_score+"��"
            }),
            new Ext.form.DisplayField({
                fieldLabel:'Υ�洦���÷�',
                value:node.attributes.abn_score +"��"
            }),
            new Ext.form.DisplayField({
                fieldLabel:'����Ա��½����÷�',
                value:node.attributes.log_score +"��"
            }),
            new Ext.form.DisplayField({
                fieldLabel:'ϵͳѲ������÷�',
                value:node.attributes.ins_score+"��"
            }),
            new Ext.form.DisplayField({
                fieldLabel:'ϵͳ��Ծ����÷�',
                value:node.attributes.act_score+"��"
            }),
            new Ext.form.DisplayField({
                fieldLabel:'�ܷ�',
                value:node.attributes.sum_score+"��"
            })
            ,
            new Ext.form.DisplayField({
                fieldLabel:'����',
                value:"100��"
            })
        ]
    });
    var select_Win = new Ext.Window({
        title:"����������ϸ",
        width:500,
        layout:'fit',
        height:280,
        modal:true,
        items:formPanel ,
        bbar:['->',{
            text:'�ر�',handler:function(){
                select_Win.close();
            }
        }]


    });
    select_Win.show();
};*/

/*function show_system_score(node){
    var formPanel = new Ext.form.FormPanel({
        frame:true,
        width:500,
        autoScroll:true,
        baseCls : 'x-plain',
        labelWidth:150,
        labelAlign:'right',
        defaultWidth:300,
        layout:'form',
        border:false,
        defaults:{
            width:250
        },
        items:[
            new Ext.form.DisplayField({
                fieldLabel:'ϵͳ',
                value:node.attributes.address
            }),
            *//*new Ext.form.DisplayField({
             fieldLabel:'ϵͳ��ʶ',
             value:node.attributes.idsystem
             }),
             new Ext.form.DisplayField({
             fieldLabel:'id',
             value:node.attributes.id
             }),*//*
            new Ext.form.DisplayField({
                fieldLabel:'ע����Ϣ�÷�',
                value:node.attributes.reg_score+"��"
            }),
            new Ext.form.DisplayField({
                fieldLabel:'Υ�洦���÷�',
                value:node.attributes.abn_score +"��"
            }),
            new Ext.form.DisplayField({
                fieldLabel:'����Ա��½����÷�',
                value:node.attributes.log_score +"��"
            }),
            new Ext.form.DisplayField({
                fieldLabel:'ϵͳѲ������÷�',
                value:node.attributes.ins_score+"��"
            }),
            new Ext.form.DisplayField({
                fieldLabel:'ϵͳ��Ծ����÷�',
                value:node.attributes.act_score+"��"
            }),
            new Ext.form.DisplayField({
                fieldLabel:'�ܷ�',
                value:node.attributes.sum_score+"��"
            }) ,
            new Ext.form.DisplayField({
                fieldLabel:'����',
                value:"100��"
            })
        ]
    });
    var select_Win = new Ext.Window({
        title:"����������ϸ",
        width:500,
        layout:'fit',
        height:280,
        modal:true,
        items:formPanel,
        bbar:['->',{
            text:'�ر�',handler:function(){
                select_Win.close();
            }
        }]
    });
    select_Win.show();
};*/

function show_system_score(node){
    var cm = new Ext.grid.ColumnModel([
        {header:'ϵͳ', dataIndex:'address'},
        {header:'ע����Ϣ�÷�', dataIndex:'reg_score'},
        {header:'Υ�洦���÷�', dataIndex:'abn_score'},
        {header:'����Ա��½����÷�', dataIndex:'log_score'},
        {header:'ϵͳѲ������÷�', dataIndex:'ins_score'},
        {header:'ϵͳ��Ծ����÷�', dataIndex:'act_score'},
        {header:'�ܷ�', dataIndex:'sum_score'}
    ]);

    var data = [
        [
            node.attributes.address,
            node.attributes.reg_score,
            node.attributes.abn_score,
            node.attributes.log_score,
            node.attributes.ins_score,
            node.attributes.act_score,
            node.attributes.sum_score
        ]
    ];

    var store = new Ext.data.Store({
        proxy:new Ext.data.MemoryProxy(data),
        reader:new Ext.data.ArrayReader({},[
            {name:'address'},
            {name:'reg_score'},
            {name:'abn_score'},
            {name:'log_score'} ,
            {name:'ins_score'} ,
            {name:'act_score'} ,
            {name:'sum_score'}
        ])
    });
    store.load();

    var gridPanel = new Ext.grid.GridPanel({
        renderTo:win,
        store:store,
        height:300,
        cm:cm
    });

    var win = new Ext.Window({
        title:"��������",
        modal:true,
        width:650,
        height:300,
        layout:'fit',
        items:gridPanel,
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

function show_address_score(node){
    var cm = new Ext.grid.ColumnModel([
        {header:'����', dataIndex:'address'},
        {header:'ע����Ϣ�÷�', dataIndex:'reg_score'},
        {header:'Υ�洦���÷�', dataIndex:'abn_score'},
        {header:'����Ա��½����÷�', dataIndex:'log_score'},
        {header:'ϵͳѲ������÷�', dataIndex:'ins_score'},
        {header:'ϵͳ��Ծ����÷�', dataIndex:'act_score'},
        {header:'�ܷ�', dataIndex:'sum_score'}
    ]);

    var data = [
        [
            node.attributes.address,
            node.attributes.reg_score,
            node.attributes.abn_score,
            node.attributes.log_score,
            node.attributes.ins_score,
            node.attributes.act_score,
            node.attributes.sum_score
        ]
    ];

    var store = new Ext.data.Store({
        proxy:new Ext.data.MemoryProxy(data),
        reader: new Ext.data.ArrayReader({}, [
            {name:'address',mapping: 0},
            {name:'reg_score',mapping: 1},
            {name:'abn_score',mapping: 2},
            {name:'log_score',mapping: 3} ,
            {name:'ins_score',mapping: 4} ,
            {name:'act_score',mapping: 5} ,
            {name:'sum_score',mapping: 6}
        ])
    });
    store.load();

    var gridPanel = new Ext.grid.GridPanel({
        renderTo:win,
        viewConfig:{
            forceFit:true //��grid�����Զ�����grid���������ȣ�����һ��һ�е��趨���ȡ�
        },
        height:300,
        border:false,
        store:store,
        cm:cm
    });

    var win = new Ext.Window({
        title:"��������",
        width:650,
        height:300,
        layout:'fit',
        modal:true,
        items:gridPanel,
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

function setWidth(){
    return 991;
}