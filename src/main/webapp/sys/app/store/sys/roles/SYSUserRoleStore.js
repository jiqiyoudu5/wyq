Ext.define('EJ.store.sys.roles.SYSUserRoleStore', {
			extend : 'Ext.data.Store',
			model : 'EJ.model.sys.roles.SYSRoleModel',
			proxy : {
				type : 'rest',
				url : 'user/finduserrolesbyuser',
				reader : {
					type : 'json'
				}
			}
		});