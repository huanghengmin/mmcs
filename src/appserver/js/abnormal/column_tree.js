/*
 * Ext JS Library 2.0.2
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing＠extjs.com
 *
 * extjs.com/license
 */

Ext.onReady(function(){
    var treeLoader =  new Ext.tree.TreeLoader({
//        dataUrl:'../../SysAssessmentAction_findSysAssessment.action',
//        dataUrl:'../../SysAssessmentAction_findSingleAssessment.action',
//        dataUrl:'column_tree_data.js',
        uiProviders:{
            'col': Ext.tree.ColumnNodeUI
        }
    });

    //定义树的跟节点
    var root = new Ext.tree.AsyncTreeNode({
        id:"1",//根节点id
        levelCode:'310000',
        draggable:false,
        expanded :true,
        text:"根结点"
    });

    var tree = new Ext.tree.ColumnTree({
        el:'tree-ct',
        layout:'fit',
        rootVisible:false,
        autoScroll:true,
        animate:true,//开启动画效果
        title: '考核评比',
        columns:[
            {header: "地区",align:'center', sortable: true, dataIndex: 'address',width:Ext.getBody().getWidth()/7+60},
            {header: "注册信息得分", align:'center', sortable: true, dataIndex: 'reg_score',width:Ext.getBody().getWidth()/7},
            {header: "违规处理得分",align:'center', sortable: true, dataIndex: 'abn_score',width:Ext.getBody().getWidth()/7},
            {header: "管理员登陆情况得分", align:'center', sortable: true, dataIndex: 'log_score',width:Ext.getBody().getWidth()/7},
            {header: "系统巡检情况得分", align:'center', sortable: true,dataIndex: 'ins_score',width:Ext.getBody().getWidth()/7},
            {header: "系统活跃情况得分",align:'center', sortable: true,dataIndex: 'act_score',width:Ext.getBody().getWidth()/7} ,
            {header: "总分",align:'center', sortable: true,dataIndex: 'sum_score',width:Ext.getBody().getWidth()/7-60}/*,
             {header: "操作标记",width:200,align:'center', sortable: true,dataIndex: 'show_flag',renderer:show_flag*//*,width:Ext.getBody().getWidth()/7*//*}*/
        ],
        loader:treeLoader,
        root: root
    });
    tree.on('dblclick', function(node, event){
        if(node.leaf){  //如果是叶节点
            show_system_score(node);
        }else{
            show_address_score(node);
        }
    });

    //增加右键点击事件
    /*    tree.on('contextmenu', function(node, event) {// 声明菜单类型
     event.preventDefault();// 阻止浏览器默认右键菜单显示
     node.select(); //节点选中
     if(node.leaf){
     alert("叶子结点!")
     } else{
     alert("非叶子结点!");
     }
     right_menu.showAt(event.getXY());// 取得鼠标点击坐标，展示菜单
     });

     var right_menu= new Ext.menu.Menu({
     //右击事件
     items:[{
     xtype:"button",
     text:"添加节点",
     iconCls:"add",
     pressed:true,
     handler : function(tree) {
     var selectedNode = tree.getSelectionModel().getSelectedNode();   //此处为给选中的节点下添加子节点
     alert(selectedNode.attributes.address)
     }
     },{
     xtype:"button",
     text:"修改节点",
     iconCls:"replace",
     pressed:true,
     handler : function(tree) {
     onUpdate();
     }
     },{
     xtype:"button",
     text:"删除节点",
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
 '<font  id = "flag_delete" style="color:green;" onclick = "deleteSysAssessment()"><u>删除</u></font> '+
 '<font  id = "flag_delete" style="color:green;" onclick = "updateSysAssessment()"><u>更新</u></font> '
 );
 }

 function deleteSysAssessment(){
 alert('delete');
 }

 function updateSysAssessment(){
 alert('update');
 }*/

function show_address_score(node){
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
                fieldLabel:'地区',
                value:node.attributes.address
            }),
            new Ext.form.DisplayField({
                fieldLabel:'注册信息得分',
                value:node.attributes.reg_score+"分"
            }),
            new Ext.form.DisplayField({
                fieldLabel:'违规处理得分',
                value:node.attributes.abn_score +"分"
            }),
            new Ext.form.DisplayField({
                fieldLabel:'管理员登陆情况得分',
                value:node.attributes.log_score +"分"
            }),
            new Ext.form.DisplayField({
                fieldLabel:'系统巡检情况得分',
                value:node.attributes.ins_score+"分"
            }),
            new Ext.form.DisplayField({
                fieldLabel:'系统活跃情况得分',
                value:node.attributes.act_score+"分"
            }),
            new Ext.form.DisplayField({
                fieldLabel:'总分',
                value:node.attributes.sum_score+"分"
            })
            ,
            new Ext.form.DisplayField({
                fieldLabel:'满分',
                value:"100分"
            })
        ]
    });
    var select_Win = new Ext.Window({
        title:"地区评比详细",
        width:500,
        layout:'fit',
        height:280,
        modal:true,
        items:formPanel ,
        bbar:['->',{
            text:'关闭',handler:function(){
                select_Win.close();
            }
        }]


    });
    select_Win.show();
};

function show_system_score(node){
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
                fieldLabel:'系统',
                value:node.attributes.address
            }),
            /*new Ext.form.DisplayField({
             fieldLabel:'系统标识',
             value:node.attributes.idsystem
             }),
             new Ext.form.DisplayField({
             fieldLabel:'id',
             value:node.attributes.id
             }),*/
            new Ext.form.DisplayField({
                fieldLabel:'注册信息得分',
                value:node.attributes.reg_score+"分"
            }),
            new Ext.form.DisplayField({
                fieldLabel:'违规处理得分',
                value:node.attributes.abn_score +"分"
            }),
            new Ext.form.DisplayField({
                fieldLabel:'管理员登陆情况得分',
                value:node.attributes.log_score +"分"
            }),
            new Ext.form.DisplayField({
                fieldLabel:'系统巡检情况得分',
                value:node.attributes.ins_score+"分"
            }),
            new Ext.form.DisplayField({
                fieldLabel:'系统活跃情况得分',
                value:node.attributes.act_score+"分"
            }),
            new Ext.form.DisplayField({
                fieldLabel:'总分',
                value:node.attributes.sum_score+"分"
            }) ,
            new Ext.form.DisplayField({
                fieldLabel:'满分',
                value:"100分"
            })
        ]
    });
    var select_Win = new Ext.Window({
        title:"地区评比详细",
        width:500,
        layout:'fit',
        height:280,
        modal:true,
        items:formPanel,
        bbar:['->',{
            text:'关闭',handler:function(){
                select_Win.close();
            }
        }]
    });
    select_Win.show();
};
