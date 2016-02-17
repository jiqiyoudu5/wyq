Ext.define('EJ.store.sys.roles.SYSRoleUserStore', {
			extend : 'Ext.data.Store',
			model : 'EJ.model.sys.user.UserModel',
			pageSize : 20,
			proxy : {
				type : 'ajax',
				reader : {
					type : 'json',
					root : 'items',
					totalProperty : 'totalCount'
				}
			}
		});