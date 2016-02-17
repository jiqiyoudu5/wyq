Ext.define('EJ.store.sys.roles.SYSRoleResourceStore', {
			extend : 'Ext.data.Store',
			model : 'EJ.model.sys.resource.SYSResourceModel',
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