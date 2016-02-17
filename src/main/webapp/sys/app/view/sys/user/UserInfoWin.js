Ext.define('EJ.view.sys.user.UserInfoWin', {
	extend : 'Ext.window.Window',
	alias : 'widget.userInfoWin',
	title : '用户详细信息',
	layout : 'fit',
	width : 500,
	modal : true,
	resizable : false,
	autoShow : true,
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
								xtype : 'textfield',
								fieldLabel : '启用状态',
								valueToRaw : function(v) {
									return v ? '启用' : '未启用';
								},
								name : 'enabled',
								labelWidth : 65,
								anchor : '95%'
							}, {
								xtype : 'textfield',
								fieldLabel : '<span style="color:red;">VIP用户</span>',
								valueToRaw : function(v) {
									return v ? '是' : '否';
								},
								name : 'vip',
								labelWidth : 65,
								anchor : '95%'
							}]
				}, {
					xtype : 'container',
					flex : 1,
					layout : 'anchor',
					items : [{
								xtype : 'datefield',
								fieldLabel : '注册时间',
								format : 'Y-m-d H:i:s',
								name : 'regTime',
								labelWidth : 80,
								anchor : '100%'
							}, {
								xtype : 'textfield',
								fieldLabel : 'Email',
								name : 'email',
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
								xtype : 'textfield',
								fieldLabel : '账号状态',
								name : 'locked',
								valueToRaw : function(v) {
									return v ? '锁定' : '未锁定';
								},
								labelWidth : 80,
								anchor : '100%'
							}, {
								fieldLabel : '<span style="color:blue;">VIP到期时间</span>',
								xtype : 'datefield',
								format : 'Y-m-d',
								editable : false,
								// readOnly : true,
								name : 'vipendtime',
								labelWidth : 80,
								anchor : '100%'
							}]
				}]
			}, {
				xtype : 'textarea',
				name : 'remark',
				fieldLabel : '备注',
				height : 90,
				labelWidth : 65,
				anchor : '100%'
			}]
		}];
		this.callParent(arguments);
	}
});