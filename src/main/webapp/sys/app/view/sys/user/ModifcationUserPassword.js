Ext.define('EJ.view.sys.user.ModifcationUserPassword', {
	extend : 'Ext.window.Window',
	alias : 'widget.modifcationUserPassword',
	title : '修改密码',
	layout : 'fit',
	width : 300,
	modal : true,
	resizable : false,
	autoShow : true,
	initComponent : function() {
		var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
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
								name : 'username',
								hidden : 'true'
							}, {
								xtype : 'textfield',
								name : 'password',
								inputType : 'password',
								fieldLabel : '密码',
								maxLength : 30,
								allowBlank : false,
								emptyText : '密码不能小于6为',
								afterLabelTextTpl : required,
								labelWidth : 65,
								anchor : '95%'
							}, {
								xtype : 'textfield',
								inputType : "password",
								maxLength : 30,
								name : 'password2',
								fieldLabel : '密码确认',
								allowBlank : false,
								afterLabelTextTpl : required,
								validator : function(value) {
									var password1 = this
											.previousSibling('[name=password]');
									return (value === password1.getValue())
											? true
											: '两次输入的密码不一致!';
								},
								labelWidth : 65,
								anchor : '95%'
							}]
				}]
			}],
			buttons : [{
						text : '提交',
						formBind : true,
						action : 'update-user-password'
					}, {
						text : '取消',
						scope : this,
						handler : this.close
				}]
		}];
		this.callParent(arguments);
	}
});