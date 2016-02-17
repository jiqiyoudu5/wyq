Ext.define('EJ.store.sys.roles.SYSRoleStore', {
			extend : 'Ext.data.Store',
			model : 'EJ.model.sys.roles.SYSRoleModel',
			pageSize : 20,
			proxy : {
				type : 'ajax',
				url : 'role/findall',
				reader : {
					type : 'json',
					root : 'items',
					totalProperty : 'totalCount'
				}
			}
		});