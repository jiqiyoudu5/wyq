Ext.define('EJ.store.sys.resource.SYSResourceStore', {
			extend : 'Ext.data.Store',
			model : 'EJ.model.sys.resource.SYSResourceModel',
			pageSize : 20,
			proxy : {
				type : 'ajax',
				url : 'resource/findall',
				reader : {
					type : 'json',
					root : 'items',
					totalProperty : 'totalCount'
				}
			}

		});