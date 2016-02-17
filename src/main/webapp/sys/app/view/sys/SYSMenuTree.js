/* root后台管理菜单显示 */
Ext.define('EJ.view.sys.SYSMenuTree', {
			extend : 'Ext.tree.Panel',
			columnLines : true,
			rootVisible : false,
			// 折叠隐藏功能
			collapsible : true,
			// 树形结构是否显示滚动条
			autoScroll : true,
			// 是否显示小箭头
			useArrows : true,
			alias : 'widget.menuTree',
			viewConfig : {
				loadMask : true,
				loadingText : '正在加载...'
			},
			store : 'sys.SYSMenuTreeStore'
		});