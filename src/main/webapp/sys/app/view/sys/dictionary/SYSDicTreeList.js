Ext.define('EJ.view.sys.dictionary.SYSDicTreeList', {
			extend : 'Ext.tree.Panel',
			id : 'SYSDicTreeList',
			alias : 'widget.sysDicTreeList',
			store : 'sys.dictionary.SYSDicTreeStore',
			columnLines : true,
			// 是否显示小箭头
			useArrows : true,
			rootVisible : false,
			viewConfig : {
				loadMask : true,
				loadingText : '正在加载字典项...'
			}
		});