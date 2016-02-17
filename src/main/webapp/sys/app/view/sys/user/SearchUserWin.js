Ext.define('EJ.view.sys.user.SearchUserWin', {
	extend : 'Ext.window.Window',
	alias : 'widget.searchUserWin',
	title : '用户查询',
	layout : 'fit',
	width : '500',
	model : true,
	resizable : false,
	initComponent : function() {
		this.items = [{
			xtype : 'form',
			border : false,
			bodyPadding : '5 5 0',
			items : [{
				xtype : 'container',
				anchor : '100%',
				layout : 'hbox',
				items : [{
					xtype : 'container',
					flex : 1,
					layout : 'anchor',
					items : [{
								xtype : 'textfield',
								fieldLabel : '用户名',
								name : 'username',
								labelWidth : 65,
								anchor : '95%'
							}, {
								xtype : 'textfield',
								fieldLabel : '公司名称',
								name : 'businessName',
								labelWidth : 65,
								anchor : '95%'
							}, {
								xtype : 'textfield',
								fieldLabel : '固话',
								name : 'tel',
								labelWidth : 65,
								anchor : '95%'
							}, {
								xtype : 'textfield',
								fieldLabel : '所在地区',
								name : 'district',
								labelWidth : 65,
								anchor : '95%'
							}, {
								xtype : 'combo',
								fieldLabel : '立即启用',
								displayField : 'text',
								valueField : 'value',
								editable : false,
								// value : true,
								store : Ext.create('Ext.data.Store', {
											fields : ['text', 'value'],
											data : [{
														'text' : '是',
														'value' : true
													}, {
														'text' : '否',
														'value' : false
													}]
										}),
								name : 'enabled',
								labelWidth : 65,
								anchor : '95%'
							}, {
								xtype : 'combo',
								fieldLabel : '<span style="color:red;">VIP用户</span>',
								displayField : 'text',
								valueField : 'value',
								editable : false,
								// value : false,
								store : Ext.create('Ext.data.Store', {
											fields : ['text', 'value'],
											data : [{
														'text' : '是',
														'value' : true
													}, {
														'text' : '否',
														'value' : false
													}]
										}),
								name : 'vip',
								labelWidth : 65,
								anchor : '95%'
							}]
				}, {
					xtype : 'container',
					flex : 1,
					layout : 'anchor',
					items : [{
								xtype : 'displayfield'
							}, {
								xtype : 'textfield',
								fieldLabel : 'Email',
								name : 'email',
								vtype : 'email',
								labelWidth : 80,
								anchor : '100%'
							}, {
								xtype : 'textfield',
								fieldLabel : '手持电话',
								name : 'phone',
								labelWidth : 80,
								anchor : '100%'
							}, {
								xtype : 'textfield',
								fieldLabel : '详细地址',
								name : 'address',
								labelWidth : 80,
								anchor : '100%'
							}, {
								xtype : 'combo',
								fieldLabel : '账号状态',
								name : 'locked',
								displayField : 'text',
								valueField : 'value',
								editable : false,
								// value : false,
								store : Ext.create('Ext.data.Store', {
											fields : ['text', 'value'],
											data : [{
														'text' : '锁定状态',
														'value' : true
													}, {
														'text' : '解锁状态',
														value : false
													}]
										}),
								labelWidth : 80,
								anchor : '100%'
							}, {
								fieldLabel : '<span style="color:blue;">VIP到期时间</span>',
								xtype : 'datefield',
								format : 'Y-m-d H:i:s',
								editable : false,
								name : 'vipendtime',
								labelWidth : 80,
								anchor : '100%'
							}]
				}]
			}],
			buttons : [{
						text : '提交',
						formBind : true,
						action : 'search-user'
					}, {
						text : '取消',
						scope : this,
						handler : this.close
					}]
		}];
		this.callParent(arguments);
	}
});