/**
 * @author nghia.khuong
 * @email nghia.khuong@enclave.vn, khuongdainghia@gmail.com
 * @function jReport core
 * @version 1.6
 * @create date: 24 Apr 2012
 * @last update: 08 Jan 2014
 */
var jReport = {
	name : 'JUnit Report',
	version : '1.6.0',
	build : '0016',
	buildDate : '23 Dec 2013',
	layout : null,
	template : null,
	settings : {
		timespan : 0,
		passColor : 'Green',
		failColor : '#DC3912',
		devMode : false,
		showDevButton : false,
		isFirst : true,
		zeroclipboard : 'resources/ZeroClipboard.swf',
		showLeftPanel : false
	},
	setup : function () {
		jReport.load();
		jReport.page.body.leftPanel.initFilter();
		jReport.utils.showTestVersionOnBrowserTitle();
		addHashChange(function (e) {
			if (jReport.hash != location.hash) {
				jReport.hash = location.hash;
				jReport.load()
			}
		});

		jReport.page.hideLoadingPanel();
		treeData = null;
	},
	load : function (initPageTypeId) {
		var pageTypeId = '0';
		var requestedPageId = this.utils.getBookmarkParameter('id');
		var isAdvancedFilter = jReport.page.body.leftPanel.isAdvancedFilter();

		if (initPageTypeId) {
			pageTypeId = initPageTypeId
		} else {
			pageTypeId = this.utils.getBookmarkParameter('pid') || getParamValue('pid', getQuery()) || '0';
		}
		if (jReport.utils.getBookmarkParameter('dev') !== '') {
			jReport.settings.devMode = (jReport.utils.getBookmarkParameter('dev') === '1');
			jReport.cookie.developerMode(jReport.settings.devMode ? '1' : '0')
		} else {
			jReport.settings.devMode = jReport.cookie.developerMode()
		}
		if (jReport.settings.devMode) {
			jReport.settings.showDevButton = true;
		}

		jReport.settings.showLeftPanel = jReport.cookie.showLeftPanel();
		/*Reload layout*/
		if (jReport.page.isReload()) {
			this.page.settings.pageType = ((pageTypeId === '1') ? 'Jenkins' : 'Report');

			if (pageTypeId == '1') {
				this.page.settings.pageType = 'Jenkins';
				this.layout = jPageLayout.jenkins;

				jReport.page.header.empty();
				jReport.page.body.empty();

				this.layout.doLayout();
				jReport.page.header.load(this.page.settings.pageType);

				if (reportData.jenkinsurl == '') {
					jReport.page.loadTemplate(jTemplate.settings.bodyPageNotFound, function () {
						jQuery(jReport.page.body.contentPanel.container).append(jQuery('#' + jTemplate.settings.bodyPageNotFound.id).jqote());
						jTemplate.pageNotFound.onload()
					});
				} else {
					jQuery(jReport.page.body.contentPanel.container).html('<iframe id="frmtopic" name="topic" src="'
						 + reportData.jenkinsurl
						 + '" frameborder="0" style="width:100%;height:100%" scrolling="auto"></iframe>');
				}
				jReport.page.body.contentPanel.dolayout();
			} else {
				this.page.settings.pageType = 'Report';
				this.layout = jPageLayout.report;

				jReport.page.data.pageData = null;

				jReport.page.data.treeDataGen();
				if (jReport.settings.isFirst || (this.page.settings.pageTypeId !== pageTypeId)) {
					jReport.page.header.empty();
					jReport.page.body.empty();

					this.layout.doLayout();
					jReport.page.header.load(this.page.settings.pageType);
				} else if (isAdvancedFilter) {
					if (jReport.page.body.leftPanel.isAdvancedFilterChange()) {
						this.page.settings.pageType = 'Report';
						this.layout = jPageLayout.report;
					}
				}
			}
			this.page.settings.pageTypeId = pageTypeId;
			/*Load page content*/
			jReport.page.body.leftPanel.load(treeData)

			if (jReport.settings.showLeftPanel == true) {
				jReport.page.body.leftPanel.show()
			} else {
				jReport.page.body.leftPanel.hide()
			}
		}

		if (isAdvancedFilter && (this.page.settings.pageId == '')) {
			requestedPageId = 'filtersummary'
		}

		jReport.page.load(requestedPageId, true);
		jReport.page.settings.reload = false;
		jReport.settings.isFirst = false;

		pageTypeId = null;
		requestedPageId = null;
		isAdvancedFilter = null;
	},
	page : {
		settings : {
			pageType : 'Report',
			pageTypeId : '0',
			pageId : '0',
			filterId : '',
			reload : false,
			entryPage : 'index.html'
		},
		load : function (pageId, reload) {
			var loadedId;
			if ((pageId != this.settings.pageId) || (reload == true)) {
				var packageId;
				var suiteId;
				var testcaseId;
				var stepId;
				var displayData;

				this.settings.pageId = pageId;
				this.showInfo(pageId);
				try {
					packageId = pageId.match(/st[0-9]*/);
					if (packageId) {
						suiteId = pageId.match(/st[0-9]*ts[0-9]*/);
						if (suiteId) {
							testcaseId = pageId.match(/st[0-9]*ts[0-9]*tc[0-9]*/);
							if (testcaseId) {
								stepId = pageId.match(/st[0-9]*ts[0-9]*tc[0-9]*sp[0-9]*/)
							}
						}
					}
				} catch (e) {
					/*logError(packageId)*/
				}
				jReport.template = null;
				if ((!pageId) || (pageId == '0') || (pageId == 'null')) {
					jReport.template = jTemplate.dashboard;
					displayData = this.filterData(pageId);
				} else if (stepId) {
					loadedId = stepId;
					displayData = this.filterData(pageId);
					if (displayData) {
						jReport.template = (typeof displayData['id'] !== 'undefined') ? jTemplate.step : jTemplate.pageNotFound;
					} else {
						jReport.template = this.body.leftPanel.isAdvancedFilter() ? jTemplate.summary : jTemplate.pageNotFound
					}
				} else if (testcaseId) {
					loadedId = testcaseId;
					displayData = this.filterData(pageId);
					if (displayData) {
						jReport.template = (displayData.length !== 0) ? jTemplate.testcase : jTemplate.pageNotFound;
					} else {
						jReport.template = this.body.leftPanel.isAdvancedFilter() ? jTemplate.summary : jTemplate.pageNotFound
					}
				} else if (suiteId) {
					loadedId = suiteId;
					displayData = this.filterData(pageId);
					if (displayData) {
						jReport.template = (displayData.length !== 0) ? jTemplate.suite : jTemplate.pageNotFound;
					} else {
						jReport.template = this.body.leftPanel.isAdvancedFilter() ? jTemplate.summary : jTemplate.pageNotFound
					}
				} else if (packageId) {
					loadedId = packageId;
					displayData = this.filterData(pageId);
					if (displayData) {
						jReport.template = (displayData.length !== 0) ? jTemplate.package : jTemplate.pageNotFound
					} else {
						jReport.template = this.body.leftPanel.isAdvancedFilter() ? jTemplate.summary : jTemplate.pageNotFound
					}
				} else {
					loadedId = pageId;
					if (parseInt(pageId) > 0) {
						displayData = null;
						jReport.template = jTemplate.pageNotFound
					} else {
						displayData = this.filterData(this.body.leftPanel.filterAdvQueryId);
						if (displayData) {
							jReport.template = ((displayData.length !== 0) || (displayData.package[0].testsuite)) ? jTemplate.summary : jTemplate.pageNotFound
						} else {
							jReport.template = jTemplate.summary
						}
					}
				}

				this.body.contentPanel.load(jReport.template, displayData);
				this.body.leftPanel.focusNode(loadedId);

				pageData = null;
				packageId = null;
				suiteId = null;
				testcaseId = null;
				stepId = null
			}
		},
		header : {
			container : '#headerPanel',
			load : function (trackingType) {
				if (trackingType == 'Report') {
					jQuery(jReport.page.header.container).append(jQuery('#' + jTemplate.settings.headerReport.id).jqote());
				} else {
					jQuery(jReport.page.header.container).append(jQuery('#' + jTemplate.settings.headerJenkins.id).jqote());
				}

				jReport.page.header.onload()
			},
			onload : function () {
				if (jReport.settings.showDevButton) {
					jQuery('#devMode').show();
				}
				if (jReport.settings.devMode) {
					jQuery('#switchToggle').parent().removeClass('btnInactive').removeClass('btnActive').addClass('btnActive').attr('title', 'Disable developer mode')
				} else {
					jQuery('#switchToggle').parent().removeClass('btnInactive').removeClass('btnActive').addClass('btnInactive').attr('title', 'Enable developer mode')
				}
				jQuery('#switchToggle').unbind('click').bind('click', function () {
					var currentFilterparam = '?id=' + (jReport.utils.getBookmarkParameter('id') || '0') + jReport.page.body.leftPanel.getFilterParam();
					this.parentNode.className = (this.parentNode.className.indexOf('btnActive') > -1) ? 'btnInactive' : 'btnActive';
					if (this.parentNode.className.indexOf('btnInactive') > -1) {
						currentFilterparam = replaceParam(currentFilterparam, 'dev', '0')

					} else {
						currentFilterparam = replaceParam(currentFilterparam, 'dev', '1')
					}
					currentFilterparam = ('#' + currentFilterparam).replace('##', '#').replace('#&', '#?');
					jReport.page.settings.reload = true;
					jReport.settings.devMode = (currentFilterparam.indexOf('dev=1') > -1);
					jReport.cookie.developerMode(jReport.settings.devMode ? '1' : '0')
					this.href = currentFilterparam;
				});
			},
			empty : function () {
				jQuery(this.container).off().unbind().empty()
			}
		},
		body : {
			leftPanel : {
				zTree : null,
				data : null,
				searchKey : '',
				container : '#bodyLeft',
				treeContainer : '#bodyLeftTree',
				treePanelContainer : '#bodyLeftTreePanel',
				filterContainer : '#bodyLeftFilter',
				filterPanel : '#filterPanel',
				filterTxt : '#filterTxt',
				filterClearBtn : '#filterPanelbtn',
				filterAdvBtn : '#filterAdvancedbtn',
				filterAdvContainer : '#filterAdvanced',
				filterAdvStatusId : '',
				filterAdvQueryId : '',
				filterErrorMessage : 'Please select at least one filter.',
				filterNodeList : [],
				filterLastValue : '',
				showFilterAdv : true,
				show : false,
				searchTip : null,
				setting : {
					callback : {
						onClick : function (event, treeId, treeNode) {
							window.location = '#id=' + treeNode.id + jReport.page.body.leftPanel.getFilterParam()
						},
						onNodeCreated : function (event, treeId, treeNode) {
							jQuery('#' + treeNode.tId + '_a').tipsy({
								gravity : 'nw',
								delayIn : 1800,
								fade : true,
								title : function () {
									return '<div style="display:inline-block;white-space:nowrap">' + jQuery(this).attr('tip') + '</div>'
								},
								html : true,
								//live: true,
								opacity : 0.7
							})
						}
					},
					view : {
						expandSpeed : 'fast',
						fontCss : function (treeId, treeNode) {
							return (!!treeNode.highlight) ? {
								'background-color' : '#EFFF79'
							}
							 : {
								'background-color' : 'inherit'
							}
						}
					},
					showTestSummaryAtALevelOfLessThan : 3,
					detectAndShowNew : true
				},
				load : function (rdata) {
					this.data = rdata;
					this.filterAdvStatusId = jReport.utils.getBookmarkParameter('status');
					jReport.page.body.leftPanel.filterAdvQueryId = jReport.utils.getBookmarkParameter('query');
					this.showFilterAdv = jReport.cookie.showFilerAdvanced() || this.isAdvancedFilter();
					jReport.cookie.showFilerAdvanced(this.showFilterAdv ? '1' : '0');

					if (this.isAdvancedFilter()) {
						this.doFilter(null, false)
					} else {
						if (!jReport.settings.devMode) {
							for (var i = 0; i < rdata.length; i++) {
								try {
									for (var j = 0; j < rdata[i].children.length; j++) {
										try {
											rdata[i].children[j].children.removeinvert('rpt', 1)
										} catch (e) {
											/*logError(e)*/
										}
									}
								} catch (e) {
									/*logError(e)*/
								}
							}
						}
						this.setting.showTestSummaryAtALevelOfLessThan = 3;
						this.setting.detectAndShowNew = jReport.settings.devMode;

						jQuery.fn.zTree.init(jQuery(this.treeContainer), clone(this.setting), rdata);
						this.zTree = jQuery.fn.zTree.getZTreeObj(this.treeContainer.replace('#', ''))
					}
					this.searchKey = jQuery('#filterTxt');
					this.searchKey.val('');
					this.loadAdvanced();
					rdata = null
				},
				loadAdvanced : function () {
					var packageId;
					var suiteId;
					var innerSt;
					this.filterReset();
					this.filterAdvStatusId = jReport.utils.getBookmarkParameter('status');
					jReport.page.body.leftPanel.filterAdvQueryId = jReport.utils.getBookmarkParameter('query');
					try {
						packageId = jReport.page.body.leftPanel.filterAdvQueryId.match(/st[0-9]*/);
						if (packageId) {
							suiteId = jReport.page.body.leftPanel.filterAdvQueryId.match(/st[0-9]*ts[0-9]*/);
						}
					} catch (e) {
						/*logError(e)*/
					};
					jQuery('#filterAdvStatus').html(this.advancedGetStatusHtml(this.filterAdvStatusId));
					this.advancedLoadStream(packageId || '');
					jQuery('#filterAdvPackages').removeAttr('disabled');

					if (packageId) {
						innerSt = this.advancedGetChildHtml(packageId, suiteId || '');
						jQuery('#filterAdvSuites').html(innerSt).removeAttr('disabled');
					}
					if (this.isAdvancedFilter()) {
						if (jReport.page.body.leftPanel.showFilterAdv) {
							this.showAdvanced()
						}
					} else {
						if (!this.showFilterAdv) {
							this.hideAdvanced()
						} else {
							this.showAdvanced()
						}
					};
					jQuery('.chzn-select').chosen({
						no_results_text : 'Oops, nothing found!',
						width : '100%',
						disable_search_threshold : 10
					})
					this.doResize();
					packageId = null;
					suiteId = null;
					innerSt = null;
				},
				doResize : function () {
					try {
						jQuery(this.container).layout({
							resize : false,
							type : 'border',
							vgap : 0,
							hgap : 0
						});
					} catch (e) {}
				},
				empty : function () {
					this.zTree = null;
					this.searchKey = null;
					this.container = null;
					this.treeContainer = null;
					this.treePanelContainer = null;
					this.filterContainer = null;
					this.filterPanel = null;
					this.filterTxt = null;
					this.filterClearBtn = null;
					this.filterAdvBtn = null;
					this.filterAdvContainer = null;
					this.filterAdvStatusId = null;
					jReport.page.body.leftPanel.filterAdvQueryId = null;
					this.filterErrorMessage = null;
					this.filterNodeList = null;
					this.filterLastValue = null;
					this.showFilterAdv = null;
					this.show = null;
					this.searchTip = null;

					this.data = null;
				},
				show : function () {
					jReport.settings.showLeftPanel = true;
					jReport.cookie.showLeftPanel(jReport.settings.showLeftPanel ? 1 : 0);
					jReport.layout.west.elem.width(jReport.layout.west.elem.attr('orgWidth'));
					jReport.layout.layout();
					jQuery('#openSidebar').hide()
				},
				hide : function () {
					jReport.settings.showLeftPanel = false;
					jReport.cookie.showLeftPanel(jReport.settings.showLeftPanel ? 1 : 0);
					jReport.layout.west.elem.attr('orgWidth', jReport.layout.west.elem.width() + 'px');

					jReport.layout.west.elem.css({
						width : '16px'
					})

					jReport.layout.layout();
					jQuery('#openSidebar').show();
				},
				toggle : function () {
					if (jReport.settings.showLeftPanel == true) {
						jReport.page.body.leftPanel.hide()
					} else {
						jReport.page.body.leftPanel.show()
					}
				},
				highLightNode : function (requestID) {
					try {
						var nodes = this.zTree.getNodesByParam("id", requestID);
						this.zTree.selectNode(nodes[0])
					} catch (e) {
						/*logError(e)*/
					}
				},
				initFilter : function () {
					try {
						this.searchKey.unbind('focus').bind('focus', function () {
							jQuery(this).select();
							if (jQuery(this).hasClass('empty')) {
								jQuery(this).removeClass('empty')
							}
						}).unbind('blur').bind('blur', function () {
							if (jQuery(this).get(0).value === '') {
								jQuery(this).addClass('empty')
							}
						}).unbind('keypress').bind('keypress', function (event) {
							var keycode = (event.keyCode ? event.keyCode : event.which);
							if (keycode == '13') {
								var value = jQuery.trim(jReport.page.body.leftPanel.searchKey.get(0).value);
								if (value.length < 5) {
									toastr.options = {
										closeButton : true,
										debug : false,
										positionClass : 'toast-search',
										onclick : null,
										showDuration : 100,
										hideDuration : 1000,
										timeOut : 2000,
										extendedTimeOut : 2000,
										showEasing : 'swing',
										hideEasing : 'linear',
										showMethod : 'fadeIn',
										hideMethod : 'fadeOut',
										width : (jQuery('#filterTree').outerWidth() - 8),
										opacity : .85
									}
									toastr.error('Search key must be at least 5 characters long!', 'Tip');
								} else if (jReport.page.body.leftPanel.filterLastValue !== value) {
									jQuery(jReport.page.body.leftPanel.treePanelContainer).ajaxMask();
									jReport.page.body.leftPanel.zTree.expandAll(false);
									setTimeout(function () {
										jReport.page.body.leftPanel.searchNode()
									}, 400)
								}
							}
						});
						jQuery('#filterAdvClear').unbind('click').bind('click', function () {
							if (jReport.page.body.leftPanel.isAdvancedFilter()) {
								jReport.page.body.leftPanel.clearFilter();
							}
						});
						jQuery('#filterAdvPackages').unbind('change').bind('change', function () {
							var suiteId = '';
							try {
								suiteId = jReport.utils.getBookmarkParameter('query').match(/st[0-9]*ts[0-9]*/);
							} catch (e) {
								/*logError(e)*/
							}
							var elemId = jQuery('#filterAdvPackages').val();
							var innerSt = jReport.page.body.leftPanel.advancedGetChildHtml(elemId, suiteId);
							jQuery('#filterAdvSuites').html(innerSt);
							setTimeout(function () {
								jQuery("#filterAdvanced .chzn-select").trigger("liszt:updated");
							}, 100);

							suiteId = null;
						});
						jQuery('#filterAdvSubmit').unbind('click').bind('click', function () {
							var filterParam = jReport.page.body.leftPanel.genFilterParam().replace('?dev=', '&dev=');
							if ((filterParam === '') || (filterParam === '&dev=1') || (filterParam === '&dev=0')) {
								alert(jReport.page.body.leftPanel.filterErrorMessage);
								jQuery('#filterAdvanced').addClass('highlight');
								setTimeout(function () {
									jQuery('#filterAdvanced').removeClass('highlight')
								}, 800)
							} else {
								filterParam = '#id=filtersummary' + filterParam;
								jReport.page.body.leftPanel.doFilter(filterParam, true);
							}
							filterParam = null;
						});
						jQuery(jReport.page.body.leftPanel.filterAdvBtn).unbind('click').bind('click', function () {
							if (jQuery(jReport.page.body.leftPanel.filterAdvContainer).css('display') === 'none') {
								jReport.page.body.leftPanel.showAdvanced();
								jReport.page.body.leftPanel.showFilterAdv = true;
								jReport.cookie.showFilerAdvanced('1')
							} else {
								jReport.page.body.leftPanel.hideAdvanced();
								jReport.page.body.leftPanel.showFilterAdv = false;
								jReport.cookie.showFilerAdvanced('0')
							}
						})
					} catch (e) {
						/*logError(e)*/
					}
				},
				advancedLoadStream : function (fid) {
					var innerSt = '';
					if (fid == '') {
						innerSt = '<option selected="selected" value="" style="color: Blue">--all--</option>';
						for (var i = 1; i < this.data.length; i++) {
							innerSt += '<option value="' + this.data[i].id + '">' + this.data[i].name + '</option>'
						}
					} else {
						innerSt = '<option value="" style="color:gray">--all--</option>';
						for (var i = 1; i < this.data.length; i++) {
							if (fid == this.data[i].id) {
								innerSt += '<option selected="selected" style="color: Blue" value="' + this.data[i].id + '">►' + this.data[i].name + '</option>'
							} else {
								innerSt += '<option value="' + this.data[i].id + '">' + this.data[i].name + '</option>'
							}
						}
					}
					jQuery('#filterAdvPackages').html(innerSt);
					innerSt = null;
				},
				advancedGetChildHtml : function (parentid, fid) {
					var queryData;
					var innerSt = '<option value="" style="color:gray">--all--</option>';
					parentid = parentid || '';
					fid = fid || '';
					if (!((parentid == '') && (fid == ''))) {
						queryData = getNode(parentid, this.data).children;
						if (queryData) {
							if (fid == '') {
								innerSt = '<option selected="selected" value="" style="color: Blue">--all--</option>';
								for (var i = 0; i < queryData.length; i++) {
									innerSt += '<option value="' + queryData[i].id + '">' + queryData[i].name + '</option>'
								}
							} else {
								innerSt = '<option value="" style="color:gray">--all--</option>';
								for (var i = 0; i < queryData.length; i++) {
									if (fid == queryData[i].id) {
										innerSt += '<option selected="selected" style="color: Blue" value="' + queryData[i].id + '">►' + queryData[i].name + '</option>'
									} else {
										innerSt += '<option value="' + queryData[i].id + '">' + queryData[i].name + '</option>'
									}
								}
							}
						} else {
							innerSt = '<option selected="selected" value="" style="color: Blue">--all--</option>'
						}
					}
					queryData = null;
					return innerSt
				},
				advancedGetStatusHtml : function (fid) {
					var innerSt = '';
					var queryData = [{
							id : 'pass',
							name : 'Pass',
							style : ('color:' + jReport.settings.passColor)
						}, {
							id : 'fail',
							name : 'Fail',
							style : ('color:' + jReport.settings.failColor)
						}
					];
					if (fid == '') {
						innerSt = '<option selected="selected" value="" style="color: Blue">--all--</option>';
						for (var i = 0; i < queryData.length; i++) {
							innerSt += '<option value="' + queryData[i].id + '" style="' + queryData[i].style + '">' + queryData[i].name + '</option>'
						}
					} else {
						innerSt = '<option value="" style="color:gray">--all--</option>';
						for (var i = 0; i < queryData.length; i++) {
							if (fid == queryData[i].id) {
								innerSt += '<option selected="selected" style="' + queryData[i].style + '" value="' + queryData[i].id + '">►' + queryData[i].name + '</option>'
							} else {
								innerSt += '<option value="' + queryData[i].id + '" style="' + queryData[i].style + '">' + queryData[i].name + '</option>'
							}
						}
					}
					queryData = null;
					return innerSt
				},
				doFilter : function (bookmark, mustupdate) {
					if (bookmark) {
						this.filterAdvStatusId = jReport.utils.getBookmarkParameter('status', bookmark);
						jReport.page.body.leftPanel.filterAdvQueryId = jReport.utils.getBookmarkParameter('query', bookmark);
						window.location.href = (bookmark.startsWith('#') == true) ? bookmark : '#' + bookmark
					} else {
						this.filterAdvStatusId = jReport.utils.getBookmarkParameter('status');
						jReport.page.body.leftPanel.filterAdvQueryId = jReport.utils.getBookmarkParameter('query')
					}
					jReport.page.data.pageData = null;
					var filteredData = this.filterData();
					if (!jReport.settings.devMode) {
						for (var i = 0; i < filteredData.length; i++) {
							try {
								for (var j = 0; j < filteredData[i].children.length; j++) {
									try {
										filteredData[i].children[j].children.removeinvert('rpt', 1)
									} catch (e) {
										/*logError(e)*/
									}
								}
							} catch (e) {
								/*logError(e)*/
							}
						}
					}
					this.setting.detectAndShowNew = jReport.settings.devMode;
					jQuery.fn.zTree.init(jQuery(this.treeContainer), clone(this.setting), filteredData);
					filteredData = null;
					this.zTree = jQuery.fn.zTree.getZTreeObj(this.treeContainer.replace('#', ''));
					if (mustupdate == true) {
						setTimeout(function () {
							if (jReport.page.settings.pageId != '') {
								jReport.page.load(jReport.page.settings.pageId, true)
							} else {
								jReport.page.load(jReport.page.body.leftPanel.filterAdvQueryId)
							}
							var expand = true;
							if (jReport.page.settings.pageId != '') {
								jReport.page.body.leftPanel.focusNode(jReport.page.settings.pageId, expand)
							} else {
								jReport.page.body.leftPanel.focusNode(jReport.page.body.leftPanel.filterAdvQueryId, expand)
							}
							expand = null;
						}, 50)
					};
					this.loadAdvanced();
					jQuery(".chzn-select").trigger("liszt:updated");
					this.filterLastValue = ''
				},
				clearFilter : function () {
					var currentNodeId = this.getCurrentNodeId();
					if (currentNodeId == 'filtersummary') {
						currentNodeId = '0'
					}
					this.filterReset();
					if (currentNodeId != '') {
						window.location.hash = '#?id=' + currentNodeId
					} else {
						window.location.hash = '#'
					}
					jReport.page.settings.reload = true;
					//jReport.load();
					this.loadAdvanced();
					jQuery(".chzn-select").trigger("liszt:updated");
					currentNodeId = null;
				},
				getCurrentNodeId : function () {
					var status = jReport.utils.getBookmarkParameter('status');
					var tmp = jReport.utils.getBookmarkParameter('query') || jReport.utils.getBookmarkParameter('id') || '0';
					if (status != '') {
						if (tmp.indexOf('?status') > -1) {
							tmp = tmp.substring(0, tmp.indexOf('?status'))
						} else if (tmp.indexOf('&status') > -1) {
							tmp = tmp.substring(0, tmp.indexOf('&status'))
						}
					}
					status = null;
					return tmp
				},
				filterReset : function () {
					jQuery('#filterAdvStatus').find('option[value=""]').prop('selected', true);
					jQuery('#filterAdvPackages').find('option[value=]""').prop('selected', true);
					jQuery('#filterAdvSuites').html(jReport.page.body.leftPanel.advancedGetChildHtml('', ''))
				},
				filterData : function () {
					var xData = [];
					/*Generate report tree*/
					var filteredData = jReport.page.filterDataAdvanced().package;
					for (var pId = 0; pId < filteredData.length; pId++) {
						var outPackage = {
							id : filteredData[pId].id,
							name : filteredData[pId].text,
							total : filteredData[pId].total,
							pass : filteredData[pId].pass,
							icon : "st",
							rpt : 1,
							status : filteredData[pId].status,
							children : []
						};
						if (filteredData[pId].testsuite) {
							for (var steIdx = 0; steIdx < filteredData[pId].testsuite.length; steIdx++) {
								var thisTestsuite = filteredData[pId].testsuite[steIdx];
								var outSuite = {
									id : thisTestsuite.id,
									name : thisTestsuite.text,
									total : thisTestsuite.total,
									pass : thisTestsuite.pass,
									icon : "ts",
									rpt : 1,
									status : thisTestsuite.status,
									children : []
								};
								for (var tceIdx = 0; tceIdx < thisTestsuite.testcase.length; tceIdx++) {
									var thisTestcase = thisTestsuite.testcase[tceIdx];
									var outTestcase = {
										id : thisTestcase.id,
										name : thisTestcase.text,
										total : thisTestcase.total,
										pass : thisTestcase.pass,
										icon : 'tc',
										status : thisTestcase.status,
										rpt : parseInt(thisTestcase.rpt),
										children : []
									};
									for (var stpIdx = 0; stpIdx < thisTestcase.teststep.length; stpIdx++) {
										var thisStep = thisTestcase.teststep[stpIdx];
										outTestcase.children.push({
											id : thisStep.id,
											action : thisStep.action,
											name : thisStep.text + ' - ' + jReport.utils.getStepAction(thisStep),
											icon : 'sp',
											pot : thisStep.screenshot.length,
											rpt : 1,
											status : thisStep.status
										});
										thisStep = null;
									}
									outSuite.children.push(outTestcase);
									/*Clean-up memory*/
									outTestcase = null;
									thisTestcase = null;
								}
								outPackage.children.push(outSuite);
								/*Clean-up memory*/
								outSuite = null;
								thisTestsuite = null;
							}
							xData.push(outPackage);
							/*Clean-up memory*/
							outPackage = null;
							xData = jQuery.makeArray(xData);
						}
					}
					filteredData = null;
					if ((!xData[0])) {
						xData = [this.data[0], {
								'id' : 'filtersummary',
								'name' : 'No items match all your desired criteria',
								'open' : 'true',
								'icon' : 'icon-noItems'
							}
						]
					} else {
						if (xData[0].id != '0') {
							xData.splice(0, 0, this.data[0], {
								'id' : 'filtersummary',
								'name' : 'Filter summary',
								'open' : 'true',
								'icon' : 'icon-filtersummary'
							})
						} else {
							xData.splice(1, 0, {
								'id' : 'filtersummary',
								'name' : 'Filte summary',
								'open' : 'true',
								'icon' : 'icon-filtersummary'
							})
						}
					}
					return xData
				},
				genFilterParam : function () {
					var filterSt = '';
					var statusId;
					var queryId = '';
					statusId = jQuery('#filterAdvStatus').val();

					var qValue = jQuery('#filterAdvSuites').val();
					if ((qValue != '') && (qValue != '--all--')) {
						queryId = jQuery('#filterAdvSuites').val();
					} else {
						qValue = jQuery('#filterAdvPackages').val();
						if ((qValue != '') && (qValue != '--all--')) {
							queryId = jQuery('#filterAdvPackages').val();
						}
					}

					if (statusId != '') {
						statusId = '&status=' + statusId;
						filterSt += statusId
					}
					if (queryId != '') {
						queryId = '&query=' + queryId;
						filterSt += queryId
					}
					if (jReport.settings.devMode) {
						filterSt += '&dev=1'
					}
					statusId = null;
					queryId = null;
					return filterSt
				},
				getFilterParam : function () {
					var rValue = '';
					var statusId = jReport.utils.getBookmarkParameter('status');
					var queryId = jReport.utils.getBookmarkParameter('query');
					if (statusId != '') {
						rValue = '&status=' + statusId
					}
					if (queryId != '') {
						rValue += '&query=' + queryId
					}
					if (jReport.settings.devMode) {
						rValue += '&dev=1'
					}
					statusId = null;
					queryId = null;
					return rValue
				},
				isAdvancedFilterChange : function () {
					return (this.genFilterParam() != this.getFilterParam())
				},
				isAdvancedFilter : function () {
					var statusId = jReport.utils.getBookmarkParameter('status');
					var queryId = jReport.utils.getBookmarkParameter('query');
					return ((queryId != '') || (statusId != ''))
				},
				showAdvanced : function () {
					if (jQuery(this.filterAdvContainer).css('display') == 'none') {
						var advancedHeight = '165';
						jQuery(this.filterAdvContainer).animate({
							height : advancedHeight
						}, 100, this.doResize);
						jQuery(this.filterAdvContainer).show();
						jQuery(this.filterAdvContainer).addClass('filteractive');
						jQuery(this.filterAdvBtn).addClass('btnactive');
						jQuery(this.filterAdvBtn + ' span').removeClass('ui-icon-carat-1-s').removeClass('ui-icon-carat-1-n');
						advancedHeight = null;
					}
				},
				hideAdvanced : function () {
					if (jQuery(this.filterAdvContainer).css('display') != 'none') {
						jQuery(this.filterAdvContainer).animate({
							height : '0'
						}, 100, this.doResize);
						jQuery(this.filterAdvContainer).hide().removeClass('filteractive');
						jQuery(this.filterAdvBtn).removeClass('btnactive').removeClass('ui-icon-carat-1-n');
						jQuery(this.filterAdvBtn + ' span').addClass('ui-icon-carat-1-s')
					}
				},
				doResize : function () {
					var $pane = jQuery(jReport.page.body.leftPanel.container);
					var $content = $pane.children('.ui-widget-content'),
					paneHdrH = jQuery(jReport.page.body.leftPanel.filterContainer).outerHeight();
					$content.height($pane.innerHeight() - paneHdrH - 10);
					$pane = null;
					$content = null;
				},
				clearSearch : function () {
					jQuery('#filterPanelbtn').parent().hide();
					this.searchKey.val('');
					this.filterLastValue = '';
					this.searchUpdateNodes(false);

					toastr.clear();
				},
				searchNode : function () {
					this.searchKey.addClass('filterBoxS');
					jQuery(this.filterClearBtn).parent().show();
					var value = jQuery.trim(this.searchKey.get(0).value);
					if (this.searchKey.hasClass('empty')) {
						jQuery(this.filterClearBtn).parent().hide();
						value = ''
					}
					if ((this.filterLastValue === value) || (value.length < 5)) {
						this.stopSearch();
						return
					}
					this.searchUpdateNodes(false);
					this.filterLastValue = value;
					if (value === '') {
						jQuery(this.filterClearBtn).parent().hide();
						this.stopSearch();
						return
					}
					this.filterNodeList = this.zTree.getNodesByParamFuzzy('name', value);

					if (this.filterNodeList.length == 0) {
						toastr.options = {
							closeButton : true,
							debug : false,
							positionClass : 'toast-search',
							onclick : null,
							showtime : 100,
							hidetime : 1000,
							timeOut : 2000,
							extendedTimeOut : 2000,
							showEasing : 'swing',
							hideEasing : 'linear',
							showMethod : 'fadeIn',
							hideMethod : 'fadeOut',
							width : (jQuery('#filterTree').outerWidth() - 8),
							opacity : .85
						}
						toastr.error('Your search for "<span style=\'background-color: #EFFF79;\'>' + value + '</span>" returned no results.', 'Search result');
					} else {
						toastr.options = {
							closeButton : true,
							debug : false,
							positionClass : 'toast-search',
							onclick : null,
							showtime : 100,
							hidetime : 1000,
							timeOut : 3000,
							extendedTimeOut : 2000,
							showEasing : 'swing',
							hideEasing : 'linear',
							showMethod : 'fadeIn',
							hideMethod : 'fadeOut',
							width : (jQuery('#filterTree').outerWidth() - 8),
							opacity : .85
						}
						toastr.success('<span style="font-weight:normal">' + this.filterNodeList.length + ' item' + ((this.filterNodeList.length > 1) ? 's' : '') + ' matching your search terms. </span>', 'Search result');
						this.searchUpdateNodes(true);
					}
					this.searchKey.focus()
				},
				searchUpdateNodes : function (highlight) {
					var eFocus = (this.filterNodeList.length <= 1);
					for (var i = 0, l = this.filterNodeList.length; i < l; i++) {
						if (highlight == true) {
							this.searchProcessNode(this.filterNodeList[i], eFocus)
						}
						this.filterNodeList[i].highlight = highlight;
						this.zTree.updateNode(this.filterNodeList[i])
					}
					try {
						this.zTree.cancelSelectedNode(this.filterNodeList[this.filterNodeList.length - 1])
					} catch (e) {
						/*logError(e)*/
					}
					this.stopSearch();
					eFocus = null;
				},
				stopSearch : function () {
					this.searchKey.removeClass('filterBoxS');
					try {
						setTimeout(function () {
							jQuery(jReport.page.body.leftPanel.treePanelContainer).ajaxMask({
								stop : true
							})
						}, 200)
					} catch (e) {
						/*logError(e)*/
					}
				},
				searchProcessNode : function (node, ensurefocus) {
					try {
						if (typeof node.children == 'undefined') {
							this.zTree.expandNode(node.getParentNode(), true, true, true)
						} else {
							this.zTree.expandNode(node)
						}
					} catch (e) {
						/*logError(e)*/
					}
				},
				focusNode : function (nodeId, expand) {
					nodeId = (nodeId ? nodeId : '0');
					var node = null;
					try {
						node = this.zTree.getNodesByParam('id', nodeId)[0];
						this.zTree.selectNode(node);
						if (expand == true) {
							this.zTree.expandNode(node, true, false, false)
						}
					} catch (e) {
						/*logError(e)*/
					}
					node = null
				}
			},
			contentPanel : {
				container : '#bodyContent',
				load : function (template, rdata) {
					this.empty();
					template.load(this.container, rdata);
					this.dolayout()
				},
				empty : function () {
					jQuery(this.container).off().unbind().empty()
				},
				dolayout : function () {
					//jReport.layout.initContent('center')
				}
			},
			empty : function () {
				jReport.page.header.empty();
				jReport.page.body.contentPanel.empty();
			}
		},
		empty : function () {
			layout = null;
			template = null;
			jQuery(document).off().unbind().empty();
		},
		data : {
			pageData : null,
			initTree : function () {
				treeData = null;
				treeData = [{
						id : 0,
						name : "Global Overview",
						open : "true",
						iconSkin : "icon-dashboard"
					}
				]
			},
			treeDataGen : function () {
				this.initTree();
				if (typeof(reportData.package) != "undefined") {
					for (var pckIdx = 0; pckIdx < reportData.package.length; pckIdx++) {
						var thisPackage = reportData.package[pckIdx];
						var outPackage = {
							id : thisPackage.id,
							name : thisPackage.text,
							total : thisPackage.total,
							pass : thisPackage.pass,
							//isnew : thisPackage.isnew,
							icon : "st",
							rpt : 1,
							status : thisPackage.status,
							children : []
						};

						for (var steIdx = 0; steIdx < reportData.package[pckIdx].testsuite.length; steIdx++) {
							var thisTestsuite = reportData.package[pckIdx].testsuite[steIdx];
							var outSuite = {
								id : thisTestsuite.id,
								name : thisTestsuite.text,
								total : thisTestsuite.total,
								pass : thisTestsuite.pass,
								icon : "ts",
								rpt : 1,
								status : thisTestsuite.status,
								children : []
							};
							for (var tceIdx = 0; tceIdx < thisTestsuite.testcase.length; tceIdx++) {
								var thisTestcase = thisTestsuite.testcase[tceIdx];
								var outTestcase = {
									id : thisTestcase.id,
									name : thisTestcase.text,
									total : thisTestcase.total,
									pass : thisTestcase.pass,
									icon : 'tc',
									status : thisTestcase.status,
									rpt : parseInt(thisTestcase.rpt),
									children : []
								};
								if (thisTestcase.teststep) {
									for (var stpIdx = 0; stpIdx < thisTestcase.teststep.length; stpIdx++) {
										try {
											var thisStep = thisTestcase.teststep[stpIdx];
											outTestcase.children.push({
												id : thisStep.id,
												action : thisStep.action,
												name : thisStep.text + ' - ' + jReport.utils.getStepAction(thisStep),
												icon : jReport.utils.getStepIcon(thisStep.action),
												pot : thisStep.screenshot.length,
												rpt : 1,
												status : thisStep.status
											});
										} catch (e) {}
									}
								}
								outSuite.children.push(outTestcase);
								/*Clean-up memory*/
								thisTestcase = null;
								outTestcase = null;
							}
							outPackage.children.push(outSuite);
							/*Clean-up memory*/
							thisTestsuite = null;
							outSuite = null
						}
						treeData.push(outPackage);
						/*Clean-up memory*/
						thisPackage = null;
						outPackage = null;
					}
				}
			}
		},
		filterData : function (pageId) {
			if (!this.body.leftPanel.isAdvancedFilter()) {
				if ((!pageId) || (pageId == '0') || (pageId == 'null')) {
					return reportData
				} else {
					return getObj(pageId, reportData);
				}
			} else {
				if ((pageId == '0') || (pageId == 'null')) {
					return reportData
				} else if (jReport.page.settings.pageId == 'filtersummary') {
					return jReport.page.filterDataAdvanced();
				}
				return getObj(pageId, jReport.page.filterDataAdvanced().package)
			}
		},
		filterDataAdvanced : function () {
			if (jReport.page.data.pageData != null) {
				return jReport.page.data.pageData
			} else {
				var packageId;
				var suiteId;
				var thisSuite = null;
				var filteredData = null;

				jReport.page.body.leftPanel.filterAdvStatusId = jReport.utils.getBookmarkParameter('status');
				jReport.page.body.leftPanel.filterAdvQueryId = jReport.utils.getBookmarkParameter('query');

				var fStatus = (jReport.page.body.leftPanel.filterAdvStatusId.length == 0) ? '-1' : ((jReport.page.body.leftPanel.filterAdvStatusId == 'pass') ? '1' : '0');
				try {
					packageId = jReport.page.body.leftPanel.filterAdvQueryId.match(/st[0-9]*/);
					suiteId = jReport.page.body.leftPanel.filterAdvQueryId.match(/st[0-9]*ts[0-9]*/);
				} catch (e) {
					/*logError(e)*/
				}
				if (jReport.page.body.leftPanel.filterAdvQueryId != '') {
					if (packageId) {
						for (var pckIdx = 0; pckIdx < reportData.package.length; pckIdx++) {
							if (reportData.package[pckIdx].id == packageId) {
								filteredData = clone(reportData.package[pckIdx]);
								break
							}
						}
					}
					if (suiteId) {
						for (var tsIdx = 0; tsIdx < filteredData.testsuite.length; tsIdx++) {
							if (filteredData.testsuite[tsIdx].id == suiteId) {
								thisSuite = filteredData.testsuite[tsIdx];
								filteredData.testsuite = [];
								filteredData.testsuite[0] = thisSuite;
								break
							}
						}
					}
					if (!filteredData.package) {
						var tmp = {
							package : [filteredData]
						};
						filteredData = tmp;
						tmp = null;
					}
				} else /*if (fStatus != '-1')*/
				{
					filteredData = clone(reportData);
				}

				filteredData.total = 0;
				filteredData.pass = 0;
				filteredData.fail = 0;
				filteredData.steps = 0;
				filteredData.time = 0;
				filteredData.gtime = 0;
				var maxTime = new Date("2099/01/01");
				var minTime = new Date("1999/01/01");

				var reportTimeStart = maxTime;
				var reportTimeEnd = minTime;
				var reportTimeEndVal = 0;

				for (var pIdx = 0; pIdx < filteredData.package.length; pIdx++) {
					thisSuite = null;
					/*Filter by status*/
					if (fStatus != '-1') {
						var tsIdx = 0;
						while (tsIdx < filteredData.package[pIdx].testsuite.length) {
							var bfeTestCaseLen = 0;
							var aftTestCaseLen = 0;

							/*Store testcase length before find and remove*/
							bfeTestCaseLen = filteredData.package[pIdx].testsuite[tsIdx].testcase.length;
							/*Remove all testcase have status <> fStatus*/
							filteredData.package[pIdx].testsuite[tsIdx].testcase.removeinvert('status', fStatus);
							/*Get testcase length after remove*/
							aftTestCaseLen = filteredData.package[pIdx].testsuite[tsIdx].testcase.length;

							if (bfeTestCaseLen != aftTestCaseLen) {
								if (aftTestCaseLen == 0) {
									filteredData.package[pIdx].testsuite.splice(tsIdx, 1);
								}
								continue;
							}
							/*Remove current testsuite if empty*/
							if (filteredData.package[pIdx].testsuite[tsIdx].testcase) {
								if (filteredData.package[pIdx].testsuite[tsIdx].testcase.length == 0) {
									filteredData.package[pIdx].testsuite.splice(tsIdx, 1);
									continue;
								}
							} else {
								filteredData.package[pIdx].testsuite.splice(tsIdx, 1);
								continue
							}
							tsIdx++
						}
					}

					/*Calc pass/fail/total*/
					filteredData.package[pIdx].total = 0;
					filteredData.package[pIdx].pass = 0;
					filteredData.package[pIdx].fail = 0;
					filteredData.package[pIdx].steps = 0;
					filteredData.package[pIdx].time = 0;

					if (filteredData.package[pIdx].testsuite.length == 0) {
						delete filteredData.package[pIdx].testsuite;
					} else {
						for (var tsIdx = 0; tsIdx < filteredData.package[pIdx].testsuite.length; tsIdx++) {
							thisSuite = filteredData.package[pIdx].testsuite[tsIdx];
							thisSuite.total = 0;
							thisSuite.pass = 0;
							thisSuite.fail = 0;
							thisSuite.steps = 0;
							thisSuite.time = 0;
							for (var tcIdx = 0; tcIdx < filteredData.package[pIdx].testsuite[tsIdx].testcase.length; tcIdx++) {
								var thisTC = filteredData.package[pIdx].testsuite[tsIdx].testcase[tcIdx];
								if (thisTC.rpt == '1') {
									thisSuite.total += 1;
									if (thisTC.status == '1') {
										thisSuite.pass += 1;
									} else {
										thisSuite.fail += 1;
									}
									thisSuite.steps += thisTC.teststep.length;
								}
								thisSuite.time += Number(thisTC.time);

								thisTC = null
							}
							filteredData.package[pIdx].total += Number(thisSuite.total);
							filteredData.package[pIdx].pass += Number(thisSuite.pass);
							filteredData.package[pIdx].fail += Number(thisSuite.fail);
							filteredData.package[pIdx].steps += Number(thisSuite.steps);
							filteredData.package[pIdx].time += Number(thisSuite.time);
							thisSuite = null;
						}
					}
					/*Calc grid exec time*/
					filteredData.package[pIdx].timestamp = maxTime;

					if (filteredData.package[pIdx].testsuite) {
						var packageTimeStart = maxTime;
						var packageTimeEnd = minTime;
						var packageTimeEndVal = 0;

						for (var tsIdx = 0; tsIdx < filteredData.package[pIdx].testsuite.length; tsIdx++) {
							filteredData.package[pIdx].testsuite[tsIdx].gtime = 0;
							var suiteGridTime = 0;
							var suiteTimeStart = maxTime;
							var suiteTimeEnd = minTime;
							var suiteTimeEndVal = 0;

							for (var tcIdx = 0; tcIdx < filteredData.package[pIdx].testsuite[tsIdx].testcase.length; tcIdx++) {
								var timeNumber = Number(filteredData.package[pIdx].testsuite[tsIdx].testcase[tcIdx].time);
								var thisTCTimeStamp = new Date(filteredData.package[pIdx].testsuite[tsIdx].testcase[tcIdx].timestamp);
								/*Min time*/
								if (thisTCTimeStamp < suiteTimeStart) {
									suiteTimeStart = thisTCTimeStamp;
								}
								if (thisTCTimeStamp < packageTimeStart) {
									packageTimeStart = thisTCTimeStamp;
								}
								if (thisTCTimeStamp < reportTimeStart) {
									reportTimeStart = thisTCTimeStamp;
								}
								/*Max time*/
								if (thisTCTimeStamp > suiteTimeEnd) {
									suiteTimeEnd = thisTCTimeStamp;
									suiteTimeEndVal = timeNumber;
								}
								if (thisTCTimeStamp > packageTimeEnd) {
									packageTimeEnd = thisTCTimeStamp;
									packageTimeEndVal = timeNumber;
								}
								if (thisTCTimeStamp > reportTimeEnd) {
									reportTimeEnd = thisTCTimeStamp;
									reportTimeEndVal = timeNumber;
								}
								/*Global timestamp*/
								if (filteredData.package[pIdx].timestamp > thisTCTimeStamp) {
									filteredData.package[pIdx].timestamp = thisTCTimeStamp;
								}
								timeNumber = null;
							}

							/*Calc suite grid exec time*/
							if ((suiteTimeStart != maxTime) && (suiteTimeEnd != minTime)) {
								suiteGridTime = ((suiteTimeEnd - suiteTimeStart) / 1000) + suiteTimeEndVal;
							} else {
								suiteGridTime = filteredData.package[pIdx].testsuite[tsIdx].time;
							}
							filteredData.package[pIdx].testsuite[tsIdx].gtime = suiteGridTime;
							filteredData.package[pIdx].gtime += suiteGridTime;
							/*Global*/
							if (filteredData.package[pIdx].status == '1') {
								if (filteredData.package[pIdx].status == '0') {
									filteredData.package[pIdx].status = '0';
								}
							}
						}

						/*Calc package grid exec time*/
						if ((packageTimeStart != maxTime) && (packageTimeEnd != minTime)) {
							filteredData.package[pIdx].gtime = ((packageTimeEnd - packageTimeStart) / 1000) + packageTimeEndVal;
						}
						filteredData.package[pIdx].timestamp = packageTimeStart;
					}
					filteredData.total += filteredData.package[pIdx].total;
					filteredData.pass += filteredData.package[pIdx].pass;
					filteredData.fail += filteredData.package[pIdx].fail;
					filteredData.steps += filteredData.package[pIdx].steps;
					filteredData.time += Number(filteredData.package[pIdx].time);
				}

				/*Calc suite grid exec time*/
				if ((reportTimeStart != maxTime) && (reportTimeEnd != minTime)) {
					filteredData.gtime = ((reportTimeEnd - reportTimeStart) / 1000) + reportTimeEndVal;
				} else {
					filteredData.gtime = filteredData.time;
				}
				/*Fix data*/
				filteredData.text = 'Ellipse';
				if (!filteredData.timestamp) {
					filteredData.timestamp = filteredData.package[0].timestamp;
				}
				/*Remove empty package*/
				var i = 0;
				while (i < filteredData.package.length) {
					if (typeof filteredData.package[i].testsuite == 'undefined') {
						filteredData.package.splice(i, 1);
					} else {
						i++;
					}
				}

				jReport.page.data.pageData = filteredData;
				filteredData = null;
				return jReport.page.data.pageData
			}
		},
		isReload : function () {
			var currentQuery = jReport.utils.getBookmarkParameter('query');
			var pageTypeId = (jReport.utils.getBookmarkParameter('pid') || getParamValue('pid', getQuery()) || '0');

			if (currentQuery != jReport.page.settings.filterId) {
				jReport.page.settings.filterId = currentQuery;
				return true
			}

			return (jReport.settings.isFirst || this.settings.reload || (this.settings.pageTypeId !== pageTypeId));
		},
		showInfo : function (requestedId) {
			var thispackage = {};
			var isDone = false;
			var globalPageTitle = 'Global Overview';
			var PageTitlePrefix = ' summary';
			var pageTitle = globalPageTitle;
			var pageBreacum = '';
			var pageObjIconCls = 'global-title';
			var currentFilterparam = jReport.page.body.leftPanel.getFilterParam();

			var packageId;
			var suiteId;
			var testcaseId;
			var stepId;

			packageId = requestedId.match(/st[0-9]*/);
			if (packageId) {
				suiteId = requestedId.match(/st[0-9]*ts[0-9]*/);
				if (suiteId) {
					testcaseId = requestedId.match(/st[0-9]*ts[0-9]*tc[0-9]*/);
					if (testcaseId) {
						stepId = requestedId.match(/st[0-9]*ts[0-9]*tc[0-9]*sp[0-9]*/);
					}
				}
			}
			var headerObj = document.getElementById('headerPageTitle');
			var breacumObj = document.getElementById('headerPageBreacum');
			jReport.page.body.leftPanel.highLightNode(requestedId);
			try {
				if (headerObj && breacumObj) {
					if (packageId) {
						for (var i = 0; i < reportData.package.length; i++) {
							if (reportData.package[i].id == packageId) {
								thispackage = reportData.package[i];
								break
							}
						}
						pageTitle = thispackage.text + PageTitlePrefix;
						pageBreacum = '<a class="link" href="#id=0' + currentFilterparam + '" title="Back to ' + globalPageTitle + '">' + globalPageTitle + '</a>' + '&gt;' + thispackage.text;
						pageObjIconCls = 'package-title'
					}
					if (suiteId) {
						isDone = false;
						for (var i = 0; i < thispackage.testsuite.length; i++) {
							if (thispackage.testsuite[i].id == suiteId) {
								pageTitle = thispackage.testsuite[i].text + PageTitlePrefix;
								pageBreacum = '<a class="link" href="' + this.settings.entryPage + '#id=0' + currentFilterparam + '" title="Back to ' + globalPageTitle + '">' + globalPageTitle + '</a>' + '&gt;' + '<a class="link" href="' + this.settings.entryPage + '#id=' + packageId + currentFilterparam + '" title="Back to ' + thispackage.text + PageTitlePrefix + '">' + thispackage.text + '</a>' + '&gt;' + thispackage.testsuite[i].text;
								pageObjIconCls = 'testsuite-title';
								break
							}
						}
					}
					if (testcaseId) {
						isDone = false;
						for (var i = 0; i < thispackage.testsuite.length; i++) {
							if (thispackage.testsuite[i].id == suiteId) {
								for (var j = 0; j < thispackage.testsuite[i].testcase.length; j++) {
									if (thispackage.testsuite[i].testcase[j].id == testcaseId) {
										pageTitle = thispackage.testsuite[i].testcase[j].text + ' summary';
										pageBreacum = '<a class="link" href="' + this.settings.entryPage + '#id=0' + currentFilterparam + '" title="Back to ' + globalPageTitle + '">' + globalPageTitle + '</a>' + '&gt;' + '<a class="link" href="' + this.settings.entryPage + '#id=' + packageId + currentFilterparam + '" title="Back to ' + thispackage.text + PageTitlePrefix + '">' + thispackage.text + '</a>' + '&gt;' + '<a class="link" href="' + this.settings.entryPage + '#id=' + suiteId + currentFilterparam + '" title="Back to ' + PageTitlePrefix + thispackage.testsuite[i].text + '">' + thispackage.testsuite[i].text + '</a>' + '&gt;' + thispackage.testsuite[i].testcase[j].text;
										pageObjIconCls = 'testcase-title';
										isDone = true;
										break
									}
								}
							}
							if (isDone) {
								break
							}
						}
					}
					if (stepId) {
						isDone = false;
						for (var i = 0; i < thispackage.testsuite.length; i++) {
							if (thispackage.testsuite[i].id == suiteId) {
								for (var j = 0; j < thispackage.testsuite[i].testcase.length; j++) {
									if (thispackage.testsuite[i].testcase[j].id == testcaseId) {
										for (var k = 0; k < thispackage.testsuite[i].testcase[j].teststep.length; k++) {
											if (thispackage.testsuite[i].testcase[j].teststep[k].id == stepId) {
												pageTitle = thispackage.testsuite[i].testcase[j].teststep[k].text + ' summary';
												pageBreacum = '<a class="link" href="' + this.settings.entryPage + '#id=0' + currentFilterparam + '" title="Back to ' + globalPageTitle + '">' + globalPageTitle + '</a>' + '&gt;' + '<a class="link" href="' + this.settings.entryPage + '#id=' + packageId + currentFilterparam + '" title="Back to ' + thispackage.text + PageTitlePrefix + '">' + thispackage.text + '</a>' + '&gt;' + '<a class="link" href="' + this.settings.entryPage + '#id=' + suiteId + currentFilterparam + '" title="Back to ' + PageTitlePrefix + thispackage.testsuite[i].text + '">' + thispackage.testsuite[i].text + '</a>' + '&gt;' + '<a class="link" href="' + this.settings.entryPage + '#id=' + testcaseId + currentFilterparam + '" title="Back to ' + thispackage.testsuite[i].testcase[j].text + PageTitlePrefix + '">' + thispackage.testsuite[i].testcase[j].text + '</a>' + '&gt;' + thispackage.testsuite[i].testcase[j].teststep[k].text;
												pageObjIconCls = 'teststep-title';
												isDone = true;
												break
											}
										}
									}
									if (isDone) {
										break
									}
								}
							}
							if (isDone) {
								break
							}
						}
					} else if (requestedId == 'filtersummary') {
						pageTitle = 'Filter Summary';
						pageBreacum = '';
						pageObjIconCls = 'filtering-title'
					}

					headerObj.innerHTML = pageTitle;
					breacumObj.innerHTML = pageBreacum;
					headerObj.parentNode.className = 'page-title ' + pageObjIconCls;
					if (pageTitle.length > 100) {
						headerObj.innerHTML = pageTitle.substring(0, 100) + '...';
					}

				}
			} catch (e) {
				/*logError(e)*/
			}
			thispackage = null;
			isDone = null;
			globalPageTitle = null;
			PageTitlePrefix = null;
			pageTitle = null;
			pageBreacum = null;
			pageObjIconCls = null;
			currentFilterparam = null;
			packageId = null;
			suiteId = null;
			testcaseId = null;
			stepId = null;
			headerObj = null;
			breacumObj = null;
		},
		loadTemplate : function (template, callback) {
			if (jQuery('#' + template.id).length == 0) {
				this.showLoadingPanel();
				jQuery.ajax({
					url : 'tmpls/' + template.file,
					dataType : 'html',
					success : function (data) {
						jQuery('body').append(data);
						jReport.page.hideLoadingPanel();
						try {
							if (typeof callback == 'function') {
								setTimeout(function() {
									callback();
								}, 500);
							}
						} catch (e) {}
					},
					error : function (jqXHR, textStatus, errorThrown) {
						jReport.page.hideLoadingPanel();
						alert(textStatus.toUpperCase() + ':\n' + errorThrown);
						try {
							if (typeof callback == 'function') {
								callback();
							}
						} catch (e) {}
					}
				})
			} else {
				try {
					if (typeof callback == 'function') {
						callback()
					}
				} catch (e) {}
			}
		},
		hideLoadingPanel : function () {
			jQuery('#loadingPanel').hide();
		},
		showLoadingPanel : function () {
			jQuery('#loadingPanel').hide();
		}
	},
	cookie : {
		keys : {
			isDevMode : 'developerMode',
			pageTypeId : 'pageTypeId',
			layoutShowLeftPanel : 'layoutShowLeftPanel',
			filterShowAdvanced : 'filterShowAdvanced',
			tabDashboardSelectedId : 'tabDashboardSelectedId',
			tabPackagesSelectedId : 'tabPackagesSelectedId',
			tabSuitesSelectedId : 'tabSuitesSelectedId',
			tabTestCaseSelectedId : 'tabTestCaseSelectedId',
			panelStepShownId : 'screenshot'
		},
		cCookie : function (cName, cValue) {
			var is_chrome = /chrome/.test(navigator.userAgent.toLowerCase());
			if (cValue === undefined) {
				return (is_chrome && localStorage) ? localStorage.getItem(cName) : jQuery.cookie(cName)
			} else {
				if (is_chrome && localStorage) {
					localStorage.setItem(cName, cValue)
				} else {
					jQuery.cookie(cName, cValue)
				}
			}
			is_chrome = null
		},
		developerMode : function (cValue) {
			if (cValue === undefined) {
				return (jReport.cookie.cCookie(jReport.cookie.keys.isDevMode) === '1')
			}
			jReport.cookie.cCookie(jReport.cookie.keys.isDevMode, cValue)
		},
		pageTypeId : function (cValue) {
			if (cValue === undefined) {
				return jReport.cookie.cCookie(jReport.cookie.keys.pageTypeId)
			}
			jReport.cookie.cCookie(jReport.cookie.keys.pageTypeId, cValue)
		},
		showLeftPanel : function (cValue) {
			if (cValue === undefined) {
				if (typeof(jReport.cookie.cCookie(jReport.cookie.keys.layoutShowLeftPanel)) == "undefined") {
					return jReport.settings.devMode
				}
				return (jReport.cookie.cCookie(jReport.cookie.keys.layoutShowLeftPanel) === '1')
			}
			jReport.cookie.cCookie(jReport.cookie.keys.layoutShowLeftPanel, cValue)
		},
		showFilerAdvanced : function (cValue) {
			if (cValue === undefined) {
				return (jReport.cookie.cCookie(jReport.cookie.keys.filterShowAdvanced) === '1')
			}
			jReport.cookie.cCookie(jReport.cookie.keys.filterShowAdvanced, cValue)
		},
		dashboardTabSelectedId : function (cValue) {
			if (cValue === undefined) {
				return jReport.cookie.cCookie(jReport.cookie.keys.tabDashboardSelectedId)
			}
			jReport.cookie.cCookie(jReport.cookie.keys.tabDashboardSelectedId, cValue)
		},
		packagesTabSelectedId : function (cValue) {
			if (cValue === undefined) {
				return jReport.cookie.cCookie(jReport.cookie.keys.tabPackagesSelectedId)
			}
			jReport.cookie.cCookie(jReport.cookie.keys.tabPackagesSelectedId, cValue)
		},
		suitesTabSelectedId : function (cValue) {
			if (cValue === undefined) {
				return jReport.cookie.cCookie(jReport.cookie.keys.tabSuitesSelectedId)
			}
			jReport.cookie.cCookie(jReport.cookie.keys.tabSuitesSelectedId, cValue);
		},
		testcaseTabSelectedId : function (cValue) {
			if (cValue === undefined) {
				return jReport.cookie.cCookie(jReport.cookie.keys.tabTestCaseSelectedId)
			}
			jReport.cookie.cCookie(jReport.cookie.keys.tabTestCaseSelectedId, cValue)
		},
		panelStepShownId : function (cValue) {
			if (cValue === undefined) {
				return jReport.cookie.cCookie(jReport.cookie.keys.panelStepShownId) || ''
			}
			jReport.cookie.cCookie(jReport.cookie.keys.panelStepShownId, cValue)
		}
	},
	chart : function (elemId, total, pass) {
		var passPersent = percentage(total, pass);
		var failPersent = (100 - passPersent).toFixed(1);
		Morris.Donut({
			element : elemId,
			data : [{
					value : passPersent,
					label : 'Pass',
					color : this.settings.passColor
				}, {
					value : failPersent,
					label : 'Fail',
					color : this.settings.failColor
				}
			],
			formatter : function (x) {
				return x + "%"
			},
			backgroundColor : '#A7BEE1',
			labelColor : 'White',
			colors : [
				this.settings.passColor, this.settings.failColor
			]
		});
		passPersent = null;
		failPersent = null
	},
	utils : {
		showTestVersionOnBrowserTitle : function () {
			var browserNewTitle = '';
			if (reportData.release != '') {
				browserNewTitle = reportData.release
			}
			if (reportData.version != '') {
				browserNewTitle += ' version ' + reportData.version
			}
			if (browserNewTitle != '') {
				browserNewTitle += ' automation execution on ' + reportData.builddate
			} else {
				browserNewTitle += 'Automation execution on ' + reportData.builddate
			}
			document.title = jReport.name + ' - ' + browserNewTitle;
			browserNewTitle = null;
		},
		getBookmarkParameter : function (name, bookmark) {
			var bm;
			var rValue;
			if (bookmark) {
				bm = bookmark
			} else {
				bm = getBookmark(location.href)
			}
			rValue = decodeURI((RegExp(name + '=' + '(.+?)(&|$)').exec(bm) || [, null])[1]);
			if (rValue == 'null') {
				rValue = ''
			}
			bm = null;
			return rValue
		},
		getStepAction : function (step) {
			return (((step.action !== '') ? step.action : '')
				 + ((step.label == '') ? '' : (': ' + step.label))
				 + ((step.value !== '') ? (' (' + step.value + ')') : '')) || 'N/A'
		},
		getStepIcon : function (action) {
			var iconCls = 'sp';
			if ((action != null) && (action != '')) {
				action = action.toLowerCase();
				if (action.startsWith('Open')) {
					iconCls = 'sp-ldt';
				} else if (action.startsWith('Input')) {
					iconCls = 'sp-ipt';
				} else if (action.startsWith('ForceInput')) {
					iconCls = 'sp-ipt';
				} else if (action.startsWith('Press')) {
					iconCls = 'sp-ipt';
				} else if (action.startsWith('Save')) {
					iconCls = 'sp-smt';
				} else if (action.startsWith('Click')) {
					iconCls = 'sp-ctb';
				} else if (action.startsWith('Check')) {
					iconCls = 'sp-can';
				} else if (action.startsWith('Is')) {
					iconCls = 'sp-can';
				} else if (action.startsWith('search')) {
					iconCls = 'sp-sch';
				} else if (action.startsWith('Select')) {
					iconCls = 'sp-slt';
				}
			}
			return iconCls;
		},
		isLocalhost : function () {
			return (document.location.hostname == "localhost") || (document.location.hostname.length == 0)
		},
		isXmas : function () {
			var today = new Date();
			var dd = today.getDate();
			var mm = today.getMonth() + 1;
			return ((dd >= 20) && (mm == 12)) || ((dd <= 7) && (mm == 1))
		},
		isTet : function () {
			var today = new Date();
			var dd = today.getDate();
			var mm = today.getMonth() + 1;
			return ((dd >= 26) && (mm == 1)) || ((dd <= 6) && (mm == 2))
		}
	}
};
var jPageLayout = {
	report : {
		body : {
			id : 'body',
			elem : null
		},
		center : {
			id : 'body > .center',
			elem : null,
			minWidth : 600
		},
		north : {
			id : 'body > .north',
			elem : null,
			height : 92,
			minHeight : 92
		},
		west : {
			id : 'body > .west',
			elem : null,
			width : 350,
			minWidth : 350
		},
		init : function () {
			jPageLayout.report.body.elem = jQuery(jPageLayout.report.body.id);
			jPageLayout.report.north.elem = jQuery(jPageLayout.report.north.id);
			jPageLayout.report.center.elem = jQuery(jPageLayout.report.center.id);
			jPageLayout.report.west.elem = jQuery(jPageLayout.report.west.id);

			/*set height*/
			if (jPageLayout.report.north.height) {
				jPageLayout.report.north.elem.css({
					height : jPageLayout.report.north.height + 'px'
				})
			}
			/*set width*/
			if (jPageLayout.report.west.width) {
				jPageLayout.report.west.elem.css({
					width : jPageLayout.report.west.width + 'px'
				})
			}
		},
		layout : function () {
			try {
				jPageLayout.report.body.elem.layout();
				jReport.page.body.leftPanel.doResize();
				if (jReport.template) {
					jReport.template.doResize();
				}
			} catch (e) {}
			// This ensures that the center is never smaller than .. pixels.
			jPageLayout.report.west.elem.resizable(
				'option',
				'maxWidth',
				(jPageLayout.report.center.elem.width() + jPageLayout.report.west.elem.width()) - jPageLayout.report.center.minWidth);
		},
		doLayout : function () {
			jPageLayout.report.init();
			// Make the west and east panels resizable
			jPageLayout.report.west.elem.resizable({
				handles : 'e',
				stop : jPageLayout.report.layout,
				helper : 'ui-resizable-helper-west',
				minWidth : jPageLayout.report.west.minWidth
			});

			// Layout the west panel first
			jPageLayout.report.west.elem.show();

			// Then do the main layout.
			jPageLayout.report.layout();
			// Hook up the re-layout to the window resize event.
			jQuery(window).resize(jPageLayout.report.layout)
		},
		resizeAll : function () {
			// Then do the main layout.
			jPageLayout.report.layout();
		},
		empty : function () {
			try {
				jQuery(jPageLayout.report.body.id).off().unbind().empty();
				jQuery(jPageLayout.report.north.id).unbind().empty();
				jQuery(jPageLayout.report.center.id).unbind().empty();
				jQuery(jPageLayout.report.west.id).unbind().empty();

				jPageLayout.report.body.elem = null;
				jPageLayout.report.north.elem = null;
				jPageLayout.report.center.elem = null;
				jPageLayout.report.west.elem = null;
			} catch (e) {}
		}
	},
	jenkins : {
		body : {
			id : 'body',
			elem : null
		},
		center : {
			id : 'body > .center',
			elem : null,
			minWidth : 600
		},
		north : {
			id : 'body > .north',
			elem : null,
			height : 41,
			minHeight : 41
		},
		west : {
			id : 'body > .west',
			elem : null,
			width : 0,
			minWidth : 0
		},
		init : function () {
			this.body.elem = jQuery(this.body.id);
			this.north.elem = jQuery(this.north.id);
			this.center.elem = jQuery(this.center.id);
			this.west.elem = jQuery(this.west.id);

			/*set height*/
			if (jPageLayout.jenkins.north.height) {
				this.north.elem.css({
					height : jPageLayout.jenkins.north.height + 'px'
				})
			}
			/*set width*/
			if (jPageLayout.jenkins.west.width) {
				this.west.elem.css({
					width : jPageLayout.jenkins.west.width + 'px'
				})
			}
		},
		layout : function () {
			jPageLayout.jenkins.body.elem.layout();

			// This ensures that the center is never smaller than 400 pixels.
			jPageLayout.jenkins.west.elem.resizable(
				'option',
				'maxWidth',
				(jPageLayout.jenkins.center.elem.width() + jPageLayout.jenkins.west.elem.width()) - jPageLayout.jenkins.center.minWidth);
		},
		doLayout : function () {
			this.init();

			// Make the west and east panels resizable
			jPageLayout.jenkins.west.elem.resizable({
				handles : 'e',
				stop : jPageLayout.jenkins.layout,
				helper : 'ui-resizable-helper-west',
				minWidth : jPageLayout.jenkins.west.minWidth
			});

			// Lay out the west panel first
			this.west.elem.hide();
			// Then do the main layout.
			jPageLayout.jenkins.layout();
			// Hook up the re-layout to the window resize event.
			jQuery(window).resize(jPageLayout.jenkins.layout)
		},
		resizeAll : function () {
			//TODO: resize all
		},
		empty : function () {
			try {
				jQuery(this.body.id).off().unbind().empty();
				jQuery(this.north.id).unbind().empty();
				jQuery(this.center.id).unbind().empty();
				jQuery(this.west.id).unbind().empty();

				this.body.elem = null;
				this.north.elem = null;
				this.center.elem = null;
				this.west.elem = null;
			} catch (e) {}
		}
	}
};
var jTemplate = {
	settings : {
		headerReport : {
			id : 'tmpl_headerPanel_Report',
			file : 'header-report.tmpl'
		},
		headerJenkins : {
			id : 'tmpl_headerPanel_Jenkins',
			file : 'header-jenkins.tmpl'
		},
		bodyDashboard : {
			id : 'tmpl_bodyContent_Dashboard',
			file : 'body-dashboard.tmpl'
		},
		bodyDashboardSummary : {
			id : 'tmpl_bodyContent_Dashboard_Summary',
			file : 'body-dashboardsummary.tmpl'
		},
		bodyPackage : {
			id : 'tmpl_bodyContent_Package',
			file : 'body-package.tmpl'
		},
		bodySuite : {
			id : 'tmpl_bodyContent_Suite',
			file : 'body-suite.tmpl'
		},
		bodyTestcase : {
			id : 'tmpl_bodyContent_Testcase',
			file : 'body-testcase.tmpl'
		},
		bodyStep : {
			id : 'tmpl_bodyContent_Step',
			file : 'body-step.tmpl'
		},
		bodySummary : {
			id : 'tmpl_bodyContent_Summary',
			file : 'body-summary.tmpl'
		},
		bodySummaryStep : {
			id : 'tmpl_bodyContent_SummaryStep',
			file : 'body-summarystep.tmpl'
		},
		bodyOutlookExport : {
			id : 'tmpl_bodyContent_OutlookExport',
			file : 'body-outlookexport.tmpl'
		},
		bodyNoMatch : {
			id : 'tmpl_bodyContent_Nomatch',
			file : 'body-nomatch.tmpl'
		},
		bodyPageNotFound : {
			id : 'tmpl_bodyContent_404',
			file : 'body-pagenotfound.tmpl'
		}
	},
	helper : {
		dataPanelResize : function (containerId, callback) {
			var container = jQuery(containerId);
			var parentElID = '#' + container.parent().parent().attr('id');
			var panelH = jQuery(parentElID).outerHeight();
			var panelTitleH = jQuery(parentElID + ' .ui-widget-header').outerHeight();
			try {
				if (typeof callback == 'function') {
					callback()
				}
			} catch (e) {
				/*logError(e)*/
			}
			jQuery('#itemDetails .detailsPanel').css({
				'height' : (panelH - panelTitleH) + 'px',
				'overflow' : 'auto'
			});
			container = null;
			parentElID = null;
			panelH = null;
			panelTitleH = null;
		},
		gridPanelResize : function (containerId, callback) {
			var container = jQuery(containerId);
			var parentElID = '#' + container.parent().parent().attr('id');

			var panelH = 0;
			var panelTabH = 0;
			var slickgridControlsH = 0;
			var slickgridHeaderH = 0;
			try {
				panelH = jQuery(parentElID).outerHeight();
			} catch (e) {}
			try {
				panelTabH = jQuery(parentElID + ' .ui-widget-header').outerHeight();
			} catch (e) {}
			try {
				slickgridControlsH = jQuery(parentElID + ' .slickgrid-controls').outerHeight();
			} catch (e) {}
			if (jQuery(parentElID + ' .slickgrid-controls-secondary').is(':visible')) {
				var slickgridSecondControlsH = jQuery(parentElID + ' .slickgrid-controls-secondary').outerHeight();
				container.css({
					'height' : (panelH - panelTabH - slickgridControlsH - slickgridSecondControlsH) + 'px'
				})
			} else {
				container.css({
					'height' : (panelH - panelTabH - slickgridControlsH) + 'px'
				})
			}
			try {
				if (typeof callback == 'function') {
					callback()
				}
			} catch (e) {
				/*logError(e)*/
			}
			marginbottom = null;
			container = null;
			parentElID = null;
			panelH = null;
			panelTabH = null;
			slickgridControlsH = null;
			slickgridHeaderH = null;
		},
		gridconvertName : function (column) {
			if (!column.name) {
				throw new Error('Missing "name" definition in SlickGrid column.')
			}
			if (column.field) {
				return column.field
			}
			if (column.id) {
				return column.id
			}
			return column.name.toLowerCase().replace(/[\s\-]/ig, '_').replace(/[^_a-z0-9]/ig, '')
		},
		gridEmpty : function (obj) {
			obj.gridSetting.Grid.destroy();
			jQuery(obj.gridSetting.container).off().unbind().empty()
		},
		gridColumnInit : function (g, defaults) {
			for (var i = 0, cols = g.columns.length; i < cols; i++) {
				var converted = jTemplate.helper.gridconvertName(g.columns[i]);
				g.columns[i].field = g.columns[i].field || converted;
				g.columns[i].id = g.columns[i].id || converted;
				g.columns[i].sortable = (typeof g.columns[i].sortable == 'boolean') ? g.columns[i].sortable : true
			}
			g.options = jQuery.extend(true, {}, defaults, g.options);
			g.options.grid.originalForceFitColumns = g.options.grid.forceFitColumns;
			g.container = jQuery(g.container);
			var sessionIsActive = g.options.session;
			if (sessionIsActive) {
				g.Session = new Slick.Session(g.id, g.columns, g.options.session);
				g.columns = g.Session.getColumns()
			}
			g.View = new Slick.Data.DataView(g.container, g.options.view);
			g.Grid = new Slick.Grid(g.container, g.View.rows, g.columns, g.options.grid, g.totals);
			g.ColumnPicker = new Slick.Controls.ColumnPicker(g.Grid, g.options.columnPicker);
			if (sessionIsActive) {
				g.Session.setGrid(g.Grid);
				g.Grid.onSetAllColumns = g.Session.saveColumns
			}
			g.Grid.onColumnsReordered = function () {
				if (sessionIsActive) {
					g.Session.saveColumns()
				}
				if (g.options.view.updateTotalsOnFilter) {
					g.View.calculateTotals()
				}
			};
			g.Grid.onSort = function (column, ascending) {
				g.View.onSort(column, ascending);
				if (sessionIsActive) {
					g.Session.saveSort(column, ascending)
				}
			};
			g.View.setGrid(g.Grid);
			g.View.setColumnPicker(g.ColumnPicker);
			g.View.drawControls();
			if (g.data.length) {
				g.View.setItems(g.data);
				g.View.applyDefaultFilters()
			}
			sessionIsActive = null;
		}
	},
	dashboard : {
		container : '',
		data : {},
		testcasesData : [],
		suitesData : [],
		testcaseStatisticsColors : [],
		load : function (container, rdata) {
			this.data = rdata;
			this.container = container;
			jReport.page.loadTemplate(jTemplate.settings.bodyDashboard, function () {
				jQuery(jTemplate.dashboard.container).jqoteapp('#' + jTemplate.settings.bodyDashboard.id, jTemplate.dashboard.data);
				jTemplate.dashboard.onload();
			});
			rdata = null
		},
		onload : function () {
			jReport.chart('executionSummaryTable', this.data.total, this.data.pass);
			if (typeof(this.data.package) != "undefined") {
				if (this.gridSetting.Grid) {
					this.gridReload()
				} else {
					this.gridSetting = clone(this.gridDefaultSetting);
					this.gridSetting.data = this.gridDataBind();
					this.gridSetting.totals = this.gridDataTotalBind();
					this.gridSetting.columns[6].visible = true;
					this.gridSetting.columns[7].visible = jReport.settings.devMode;
					this.gridSetting.columns[8].visible = jReport.settings.devMode;
					this.gridSetting.columns[9].visible = jReport.settings.devMode;
					this.gridSetting.columns[10].visible = jReport.settings.devMode;
					this.gridInit(this.gridSetting)
				}
				if (jReport.settings.devMode) {
					var tabSelectedIndex = this.getTabSelectedIndex();
					jQuery('#itemDetails').tabs({
						selected : 0
					}).unbind('tabsselect').bind('tabsselect', function (event, ui) {
						var selectedTabId = jQuery(ui.panel).attr('id');
						jReport.cookie.dashboardTabSelectedId(selectedTabId);
						switch (selectedTabId) {
						case 'tstabs-2':
							jTemplate.dashboard.showTestAnalysis();
							break;
						case 'tstabs-3':
							jTemplate.dashboard.showOutlookExportPage();
							break;
						default:
							break
						}
						jTemplate.dashboard.doResize()
					});
					jQuery('#itemDetails').tabs('select', tabSelectedIndex);
				} else {
					jQuery('#itemDetails').tabs()
				}
			} else {
				jQuery('#itemDetails').tabs()
			}
			jTemplate.dashboard.doResize()
		},
		prepareTestAnalysis : function () {
			jQuery('#tsStaticTriger').unbind('click').bind('click', function () {
				jQuery('#testCaseStatistics').slideToggle('fast', function () {
					if (jQuery(this).css('display') == 'none') {
						jQuery('#tsStaticTriger').removeClass('curSelected');
						jQuery('#tsStaticTriger > span:first').removeClass('ui-icon-carat-1-n').removeClass('ui-icon-carat-1-s').addClass('ui-icon-carat-1-s')
					} else {
						jQuery('#tsStaticTriger').addClass('curSelected');
						jQuery('#tsStaticTriger > span:first').removeClass('ui-icon-carat-1-n').removeClass('ui-icon-carat-1-s').addClass('ui-icon-carat-1-n')
					}
				})
			});

			var values = [0];
			var labels = [''];
			for (var i = 0; i < this.data.package.length; i++) {
				values.push(parseInt(this.data.package[i].total));
				labels.push(this.data.package[i].text)
			}
			Raphael("totalChartPanel", 90, 90).pieChart(45, 45, 40, values, labels, 'none');

			values = [0];
			labels = [''];
			for (var i = 0; i < this.data.package.length; i++) {
				values.push(parseInt(this.data.package[i].pass));
				labels.push(this.data.package[i].text)
			}
			Raphael("passedChartpanel", 90, 90).pieChart(45, 45, 40, values, labels, 'none');

			values = [0];
			labels = [''];
			for (var i = 0; i < this.data.package.length; i++) {
				values.push(parseInt(this.data.package[i].fail));
				labels.push(this.data.package[i].text)
			}
			Raphael("failedChartpanel", 90, 90).pieChart(45, 45, 40, values, labels, 'none');

			values = null;
			labels = null;

			jQuery('#overviewTriggerPanel').unbind('click').bind('click', function () {
				jQuery('#overviewPanel').slideToggle('fast', function () {
					if (jQuery(this).css('display') == 'none') {
						jQuery('#overviewTriggerPanel').removeClass('curSelected');
						jQuery('#overviewTriggerPanel > span:first').removeClass('ui-icon-carat-1-n').removeClass('ui-icon-carat-1-s').addClass('ui-icon-carat-1-s')
					} else {
						jQuery('#overviewTriggerPanel').addClass('curSelected');
						jQuery('#overviewTriggerPanel > span:first').removeClass('ui-icon-carat-1-n').removeClass('ui-icon-carat-1-s').addClass('ui-icon-carat-1-n')
					}
				})
			});
		},
		showTestAnalysis : function () {
			if (jQuery('#tsStaticPanel').html().length < 100) {
				jReport.page.loadTemplate(jTemplate.settings.bodyDashboardSummary, function () {
					jQuery('#tsStaticPanel').jqoteapp('#' + jTemplate.settings.bodyDashboardSummary.id, jTemplate.dashboard.data);
					jTemplate.dashboard.prepareTestAnalysis();
				});
			}
		},
		showOutlookExportPage : function () {
			if (jQuery('#outlookExportPanel').html().length < 100) {
				jReport.page.loadTemplate(jTemplate.settings.bodyOutlookExport, function () {
					jQuery('#outlookExportPanel').jqoteapp('#' + jTemplate.settings.bodyOutlookExport.id, jTemplate.dashboard.data);
				})
			}
		},
		getTabSelectedIndex : function () {
			var index;
			var selectedTabId = jReport.cookie.dashboardTabSelectedId();
			if ((selectedTabId == '') || (selectedTabId == null)) {
				selectedTabId = 'tstabs-1'
			}
			index = jQuery('#itemDetails a[href="#' + selectedTabId + '"]').parent().index();
			selectedTabId = null;
			return (index == -1) ? 0 : index
		},
		gettestcasesData : function () {
			if (this.testcasesData.length == 0) {
				for (var pkgIdx = 0; pkgIdx < this.data.package.length; pkgIdx++) {
					for (var steIdx = 0; steIdx < this.data.package[pkgIdx].testsuite.length; steIdx++) {
						for (var tceIdx = 0; tceIdx < this.data.package[pkgIdx].testsuite[steIdx].testcase.length; tceIdx++) {
							this.testcasesData.push(this.data.package[pkgIdx].testsuite[steIdx].testcase[tceIdx])
						}
					}
				}
			}
		},
		getsuitesData : function () {
			if (this.suitesData.length == 0) {
				for (var pkgIdx = 0; pkgIdx < this.data.package.length; pkgIdx++) {
					for (var steIdx = 0; steIdx < this.data.package[pkgIdx].testsuite.length; steIdx++) {
						this.suitesData.push(this.data.package[pkgIdx].testsuite[steIdx])
					}

				}
			}
		},
		getTop10FastestTestCase : function () {
			this.gettestcasesData();
			this.testcasesData.sort(dynamicSortNumber('time'));
			var returnTCs = [];
			var arrlen = this.testcasesData.length;
			for (var i = 0; i < arrlen; i++) {
				returnTCs.push(this.testcasesData[i]);
				if (i >= 9) {
					break
				}
			}
			return returnTCs
		},
		getTop10SlowestTesCase : function () {
			this.gettestcasesData();
			var count = 0;
			var returnTCs = [];
			var arrlen = this.testcasesData.length;
			for (var i = (arrlen - 1); i > 0; i--) {
				returnTCs.push(this.testcasesData[i]);
				count += 1;
				if (count >= 10) {
					break
				}
			}
			count = null;
			arrlen = null;
			return returnTCs
		},
		getTop10FastestSuites : function () {
			this.getsuitesData();
			this.suitesData.sort(dynamicSortNumber('time'));
			var returnTSs = [];
			var arrlen = this.suitesData.length;
			for (var i = 0; i < arrlen; i++) {
				returnTSs.push(this.suitesData[i]);
				if (i >= 9) {
					break
				}
			}
			return returnTSs
		},
		getTop10SlowestSuites : function () {
			this.getsuitesData();
			var count = 0;
			var returnTSs = [];
			var arrlen = this.suitesData.length;
			for (var i = (arrlen - 1); i > 0; i--) {
				returnTSs.push(this.suitesData[i]);
				count += 1;
				if (count >= 10) {
					break
				}
			}
			count = null;
			return returnTSs
		},
		doResize : function () {
			try {
				jQuery(this.container).layout({
					resize : false,
					type : 'border',
					vgap : 0,
					hgap : 0
				});
			} catch (e) {}
			var resizeGrid = !jReport.settings.devMode;
			if (jReport.settings.devMode) {
				var tabSelectedIndex = this.getTabSelectedIndex();
				if (tabSelectedIndex == 0) {
					resizeGrid = true
				} else {
					jTemplate.helper.dataPanelResize(this.gridDefaultSetting.container)
				}
			}
			if (resizeGrid) {
				this.gridResize()
			}
			resizeGrid = null;
		},
		gridSetting : {},
		gridDefaultSetting : {
			id : 'gridPanel_dashboard_' + getUniqueId(),
			container : '#gridPanel',
			options : {
				session : false,
				rowCssClasses : function (item) {
					return ((item.rpt === 'Disabled') ? 'ignore' : '')
				}
			},
			columns : [{
					id : 'status',
					field : 'status',
					name : 'Status',
					sortable : true,
					resizable : false,
					minWidth : 65,
					width : 65,
					visible : true
				}, {
					id : 'text',
					field : 'text',
					name : 'Stream',
					filter : 'text',
					sortable : true,
					width : 130,
					minWidth : 130,
					visible : true
				}, {
					id : 'statusbar',
					field : 'statusbar',
					name : 'Pass/Fail',
					visible : true,
					resizable : false,
					minWidth : 120,
					width : 120,
					formatter : Slick.Formatters.passFailBar
				}, {
					id : 'total',
					field : 'total',
					name : 'Test Cases',
					minWidth : 50,
					width : 50,
					sortable : true,
					visible : true,
					cssClass : 'alignRight'
				}, {
					id : 'passlnk',
					field : 'passlnk',
					name : 'Pass',
					minWidth : 50,
					width : 50,
					sortable : true,
					visible : true,
					cssClass : 'alignRight pass'
				}, {
					id : 'failureslnk',
					field : 'failureslnk',
					name : 'Fail',
					minWidth : 50,
					width : 50,
					sortable : true,
					visible : true,
					cssClass : 'alignRight fail'
				}, {
					id : 'steps',
					field : 'steps',
					name : 'Steps',
					sortable : true,
					minWidth : 50,
					width : 50,
					visible : true,
					cssClass : 'alignRight'
				}, {
					id : 'POTs',
					field : 'pots',
					name : 'POTs',
					sortable : true,
					minWidth : 50,
					width : 50,
					visible : false,
					cssClass : 'alignRight'
				}, {
					id : 'timestamp',
					field : 'timestamp',
					name : 'Start At',
					sortable : true,
					minWidth : 60,
					width : 60,
					visible : true,
					cssClass : 'alignRight'
				}, {
					id : 'time',
					field : 'time',
					name : 'RCs Exec Time',
					sortable : true,
					minWidth : 70,
					width : 70,
					visible : false,
					cssClass : 'alignRight'
				}, {
					id : 'gtime',
					field : 'gtime',
					name : 'Grid Exec Time',
					sortable : true,
					minWidth : 70,
					width : 70,
					visible : false,
					cssClass : 'alignRight'
				}
			],
			totals : null,
			data : null
		},
		gridInit : function (g) {
			var defaults = {
				session : false,
				grid : {
					defaultMinWidth : 68,
					forceFitColumns : (g.columns.length <= 12),
					rowHeight : 21,
					showTotalsHeader : false,
					showTotalsFooter : true,
					syncColumnCellResize : true,
					autoContainerHeight : true,
					editable : true,
					rerenderOnResize : false
				},
				columnPicker : {
					fadeSpeed : 150,
					showAutoResize : false,
					showSyncResize : false
				},
				view : {
					showColumnPresets : true,
					hideTotalsOnFilter : false,
					updateTotalsOnFilter : true
				}
			};
			jTemplate.helper.gridColumnInit(g, defaults);
			defaults = null;
		},
		gridResize : function () {
			setTimeout(function () {
				jTemplate.helper.gridPanelResize(jTemplate.dashboard.gridDefaultSetting.container);
				jTemplate.dashboard.gridSetting.Grid.resizeGrid()
			}, 50)
		},
		gridReload : function () {
			jTemplate.helper.gridEmpty(this);
			this.gridSetting = clone(this.gridDefaultSetting);
			this.gridSetting.data = this.gridDataBind();
			this.gridSetting.totals = this.gridDataTotalBind();
			this.gridSetting.columns[6].visible = true;
			this.gridSetting.columns[7].visible = jReport.settings.devMode;
			this.gridSetting.columns[8].visible = jReport.settings.devMode;
			this.gridSetting.columns[9].visible = jReport.settings.devMode;
			this.gridSetting.columns[10].visible = jReport.settings.devMode;
			this.gridInit(this.gridSetting)
		},
		gridDataBind : function () {
			var xdata = [];
			var currentFilterparam = jReport.page.body.leftPanel.getFilterParam();
			for (var i = 0; i < this.data.package.length; i++) {
				xdata[i] = {
					id : this.data.package[i].id + getUniqueid(),
					statusbar : '',
					text : '<a class="tootip link"' + ' target="_self" title="View package ' + this.data.package[i].text + ' summary" href="#?id=' + this.data.package[i].id + '">' + this.data.package[i].text + '</a>',
					total : Number(this.data.package[i].total),
					steps : this.getTotalofSteps(this.data.package[i].id),
					pass : Number(this.data.package[i].pass),
					passlnk : (Number(this.data.package[i].pass) > 0) ? '<a class="tooltip pass link" target="_self" href="#id=' + this.data.package[i].id + replaceParam(replaceParam(currentFilterparam, 'status', 'pass'), 'query', this.data.package[i].id) + '">' + Number(this.data.package[i].pass) + '</a>' : '0',
					fail : Number(this.data.package[i].fail),
					failureslnk : (Number(this.data.package[i].fail) > 0) ? '<a class="tooltip fail link" target="_self" href="#id=' + this.data.package[i].id + replaceParam(replaceParam(currentFilterparam, 'status', 'fail'), 'query', this.data.package[i].id) + '">' + Number(this.data.package[i].fail) + '</a>' : '0',
					status : '<div class="ui-icon ' + ((this.data.package[i].status == '1') ? 'icon-pass' : 'icon-fail') + '">&nbsp;</div>',
					pots : this.getTotalOfPOTs(this.data.package[i].id),
					time : secondsToHms(this.data.package[i].time),
					gtime : secondsToHms(this.data.package[i].gtime),
					timestamp : formatDatetime(this.data.package[i].timestamp, 'HH:MM:ss TT')
				}
			}
			return xdata
		},
		gridDataTotalBind : function () {
			var returnItem = {
				id : '&nbsp;',
				statusbar : '&nbsp;',
				text : '<b><i>Result Total:</i></b>',
				steps : 0,
				pots : 0,
				total : 0,
				pass : 0,
				passlnk : 0,
				fail : 0,
				failureslnk : 0,
				gtime : 0,
				time : 0,
				status : 0,
				timestamp : '&nbsp;'
			};
			for (var i = 0; i < this.data.package.length; i++) {
				returnItem.total += Number(this.data.package[i].total);
				returnItem.pass += Number(this.data.package[i].pass);
				returnItem.passlnk += Number(this.data.package[i].pass);
				returnItem.fail += Number(this.data.package[i].fail);
				returnItem.failureslnk += Number(this.data.package[i].fail);
				returnItem.status += Number(this.data.package[i].status);
				returnItem.time += parseFloat(this.data.package[i].time)
			}
			returnItem.steps = this.getTotalofSteps('');
			returnItem.pots = this.getTotalOfPOTs('');
			returnItem.gtime = secondsToHms(this.data.gtime);
			returnItem.time = secondsToHms(returnItem.time);
			returnItem.statusbar = passFailBar(returnItem);
			returnItem.status = '<div class="noPadding ' + ((returnItem.status == '1') ? 'icon-pass' : 'icon-fail') + '">&nbsp;</div>';
			return returnItem
		},
		getBuildDate : function (datest) {
			return formatDatetime(datest).replace('Invalid Date', 'Unknown')
		},
		fixRCNode : function (rcNode) {
			return rcNode.replace(/(172.20.126.)/g, '').replace(/(172.31.121.)/g, '');
		},
		getTotalOfPOTs : function (packageId) {
			var total = 0;
			for (var i = 0; i < this.data.package.length; i++) {
				if ((packageId == '') || (this.data.package[i].id == packageId)) {
					for (var j = 0; j < this.data.package[i].testsuite.length; j++) {
						for (var k = 0; k < this.data.package[i].testsuite[j].testcase.length; k++) {
							if (jReport.settings.devMode || (this.data.package[i].testsuite[j].testcase[k].rpt == '1')) {
								for (var l = 0; l < this.data.package[i].testsuite[j].testcase[k].teststep.length; l++) {
									if (this.data.package[i].testsuite[j].testcase[k].teststep[l].screenshot != '') {
										total += 1
									}
								}
							}
						}
					}
					if (packageId != '') {
						break
					}
				}
			}
			return total
		},
		getTotalofSteps : function (packageId) {
			var total = 0;
			for (var i = 0; i < this.data.package.length; i++) {
				if ((packageId == '') || (this.data.package[i].id == packageId)) {
					for (var j = 0; j < this.data.package[i].testsuite.length; j++) {
						total += Number(this.data.package[i].testsuite[j].steps)
					}
					if (packageId != '') {
						break
					}
				}
			}
			return total
		}
	},
	summary : {
		container : '',
		data : {},
		load : function (container, rdata) {
			var hasResult = false;
			this.data = rdata;
			this.container = container;
			if (rdata) {
				if (rdata.package || rdata.testsuite || rdata.testcase) {
					if (rdata.package) {
						try {
							if (typeof(rdata.package[0].testsuite) != "undefined") {
								hasResult = rdata.package[0].testsuite.length
							}
						} catch (e) {}
					} else if (rdata.testsuite) {
						hasResult = rdata.testsuite.length
					} else if (rdata.testcase) {
						hasResult = rdata.testcase.length
					}
					if (hasResult) {
						jReport.page.loadTemplate(jTemplate.settings.bodySummary, function () {
							jQuery(jTemplate.summary.container).jqoteapp('#' + jTemplate.settings.bodySummary.id, jTemplate.summary.data);
							jTemplate.summary.onload();
							jTemplate.summary.doResize();
						});
					}
				}
			}
			if (!hasResult) {
				jReport.page.loadTemplate(jTemplate.settings.bodyNoMatch, function () {
					jQuery(jTemplate.summary.container).append(jQuery('#' + jTemplate.settings.bodyNoMatch.id).jqote())
				})
			}
			rdata = null
		},
		onload : function () {
			jReport.chart('executionSummaryTable', this.data.total, this.data.pass)
			jQuery('#filterDetails td.toggleIcon').unbind('click').bind('click', function (elem) {
				var detailsPanel = jQuery('#' + jQuery(this).parent().attr('id') + '-details');
				if (detailsPanel.is(':visible')) {
					jQuery('#' + jQuery(this).attr('id') + ' > span:first').removeClass('ui-icon-carat-1-n').removeClass('ui-icon-carat-1-s').addClass('ui-icon-carat-1-s');
					detailsPanel.hide()
				} else {
					jQuery('#' + jQuery(this).attr('id') + ' > span:first').removeClass('ui-icon-carat-1-n').removeClass('ui-icon-carat-1-s').addClass('ui-icon-carat-1-n');
					detailsPanel.show()
				}
			});
			jQuery('#filterDetails td.toggleStepIcon').unbind('click').bind('click', function (elem) {
				var detailsPanel = jQuery('#' + jQuery(this).parent().attr('id') + '-details');
				if (detailsPanel.is(':visible')) {
					jQuery('#' + jQuery(this).attr('id') + ' > span:first').removeClass('ui-icon-carat-1-n').removeClass('ui-icon-carat-1-s').addClass('ui-icon-carat-1-s');
					detailsPanel.hide()
				} else {
					var elId = jQuery(this).parent().attr('id');
					var stepEl = jQuery(('#' + elId + '-detailsStep'));
					jQuery('#' + jQuery(this).attr('id') + ' > span:first').removeClass('ui-icon-carat-1-n').removeClass('ui-icon-carat-1-s').addClass('ui-icon-carat-1-n');
					if (jQuery.trim(stepEl.html()).length <= 20) {
						jReport.page.loadTemplate(jTemplate.settings.bodySummaryStep, function () {
							jQuery('#' + stepEl.attr('id')).jqoteapp('#' + jTemplate.settings.bodySummaryStep.id, getObj(elId, jTemplate.summary.data));
						})
					}
					detailsPanel.show()
				}
			})
		},
		doResize : function () {
			try {
				jQuery(this.container).layout({
					resize : false,
					type : 'border',
					vgap : 0,
					hgap : 0
				});
			} catch (e) {}
		},
		filterName : function () {
			var queryParam = jReport.utils.getBookmarkParameter('status');
			if (queryParam == '') {
				queryParam = 'Executed'
			} else {
				queryParam = queryParam.capitalize() + 'ed'
			}
			return queryParam
		},
		filterType : function () {
			var rtn = '';
			var queryParam = jReport.utils.getBookmarkParameter('query');
			if (queryParam == '') {
				rtn = 'Global'
			} else {
				var packageId;
				var suiteId;
				try {
					packageId = queryParam.match(/st[0-9]*/);
					if (packageId) {
						suiteId = queryParam.match(/st[0-9]*ts[0-9]*/)
					}
				} catch (e) {
					/*logError(e)*/
				}
				if (suiteId) {
					rtn = 'Test Suite'
				} else if (packageId) {
					rtn = 'Stream'
				} else {
					rtn = 'Unknown'
				}
			}
			queryParam = null;
			packageId = null;
			suiteId = null;
			return rtn
		}
	},
	package : {
		container : '',
		data : {},
		stepCount : 0,
		potCount : 0,
		load : function (container, rdata) {
			this.data = rdata;
			this.container = container;
			this.stepCount = this.getTotalofSteps();
			this.potCount = this.getTotalOfPOTs();

			jReport.page.loadTemplate(jTemplate.settings.bodyPackage, function () {
				jQuery(jTemplate.package.container).jqoteapp('#' + jTemplate.settings.bodyPackage.id, jTemplate.package.data);
				jTemplate.package.onload()
			});
			rdata = null
		},
		onload : function () {
			jReport.chart('executionSummaryTable', jTemplate.package.data.total, jTemplate.package.data.pass);
			if (this.gridSetting.Grid) {
				this.gridReload()
			} else {
				this.gridSetting = clone(this.gridDefaultSetting);
				this.gridSetting.data = this.gridDataBind();
				this.gridSetting.totals = this.gridDataTotalBind();
				this.gridSetting.columns[6].visible = true;
				this.gridSetting.columns[8].visible = jReport.settings.devMode;
				this.gridSetting.columns[9].visible = jReport.settings.devMode;
				this.gridInit(this.gridSetting)
			}
			if (jReport.settings.devMode) {
				var tabSelectedIndex = this.getTabSelectedIndex();
				jQuery('#itemDetails').tabs({
					selected : 0
				}).unbind('tabsselect').bind('tabsselect', function (event, ui) {
					var selectedTabId = jQuery(ui.panel).attr('id');
					jReport.cookie.packagesTabSelectedId(selectedTabId);
					switch (selectedTabId) {
					case 'tstabs-2':
						jTemplate.package.showOutlookExportPage();
						break;
					default:
						break
					}
					jTemplate.package.doResize()
				});
				jQuery('#itemDetails').tabs('select', tabSelectedIndex);
			} else {
				jQuery('#itemDetails').tabs()
			}
			jTemplate.package.doResize()
		},
		showOutlookExportPage : function () {
			if (jQuery('#outlookExportPanel').html().length < 100) {
				jReport.page.loadTemplate(jTemplate.settings.bodyOutlookExport, function () {
					jQuery('#outlookExportPanel').jqoteapp('#' + jTemplate.settings.bodyOutlookExport.id, reportData);
				})
			}
		},
		doResize : function () {
			try {
				jQuery(this.container).layout({
					resize : false,
					type : 'border',
					vgap : 0,
					hgap : 0
				});
			} catch (e) {}
			var resizeGrid = !jReport.settings.devMode;
			if (jReport.settings.devMode) {
				var tabSelectedIndex = this.getTabSelectedIndex();
				if (tabSelectedIndex == 0) {
					resizeGrid = true
				} else {
					jTemplate.helper.dataPanelResize(this.gridDefaultSetting.container)
				}
			}
			if (resizeGrid) {
				this.gridResize()
			}
			resizeGrid = null;
		},
		gridSetting : {},
		gridDefaultSetting : {
			id : 'gridPanel_package_' + getUniqueId(),
			container : '#gridPanel',
			options : {
				session : false,
				rowCssClasses : function (item) {
					return ((item.rpt == 'Disabled') ? 'ignore' : '')
				}
			},
			columns : [{
					id : 'status',
					field : 'status',
					name : 'Status',
					sortable : true,
					resizable : false,
					minWidth : 65,
					width : 65,
					visible : true
				}, {
					id : 'text',
					field : 'text',
					name : 'Test Suite',
					filter : 'text',
					sortable : true,
					minWidth : 200,
					width : 210,
					visible : true
				}, {
					id : 'statusbar',
					field : 'statusbar',
					name : 'Pass/Fail',
					visible : true,
					resizable : false,
					minWidth : 120,
					width : 120,
					formatter : Slick.Formatters.passFailBar
				}, {
					id : 'total',
					field : 'total',
					name : 'Test Cases',
					sortable : true,
					minWidth : 50,
					width : 60,
					visible : true,
					cssClass : 'alignRight'
				}, {
					id : 'passlnk',
					field : 'passlnk',
					name : 'Pass',
					sortable : true,
					minWidth : 50,
					width : 50,
					visible : true,
					cssClass : 'alignRight pass'
				}, {
					id : 'failureslnk',
					field : 'failureslnk',
					name : 'Fail',
					minWidth : 50,
					width : 50,
					sortable : true,
					visible : true,
					cssClass : 'alignRight fail'
				}, {
					id : 'steps',
					field : 'steps',
					name : 'Steps',
					sortable : true,
					minWidth : 50,
					width : 50,
					visible : true,
					cssClass : 'alignRight'
				}, {
					id : 'POTs',
					field : 'pots',
					name : 'POTs',
					sortable : true,
					minWidth : 50,
					width : 50,
					visible : false,
					cssClass : 'alignRight'
				}, {
					id : 'timestamp',
					field : 'timestamp',
					name : 'Start At',
					sortable : true,
					minWidth : 80,
					width : 80,
					visible : true,
					cssClass : 'alignRight'
				}, {
					id : 'time',
					field : 'time',
					name : 'RCs Exec Time',
					sortable : true,
					minWidth : 90,
					width : 90,
					visible : false,
					cssClass : 'alignRight'
				}, {
					id : 'gtime',
					field : 'gtime',
					name : 'Grid Exec Time',
					sortable : true,
					minWidth : 70,
					width : 70,
					visible : false,
					cssClass : 'alignRight'
				}
			],
			totals : null,
			data : null
		},
		gridInit : function (g) {
			var defaults = {
				session : false,
				grid : {
					defaultMinWidth : 68,
					forceFitColumns : (g.columns.length <= 12),
					rowHeight : 21,
					showTotalsHeader : false,
					showTotalsFooter : true,
					syncColumnCellResize : true,
					autoContainerHeight : true
				},
				columnPicker : {
					fadeSpeed : 150,
					showAutoResize : false,
					showSyncResize : false
				},
				view : {
					showColumnPresets : true,
					hideTotalsOnFilter : false,
					updateTotalsOnFilter : true
				}
			};
			jTemplate.helper.gridColumnInit(g, defaults);
			defaults = null;
		},
		gridResize : function () {
			setTimeout(function () {
				jTemplate.helper.gridPanelResize(jTemplate.package.gridDefaultSetting.container);
				jTemplate.package.gridSetting.Grid.resizeGrid()
			}, 50)
		},
		getTabSelectedIndex : function () {
			var index;
			var selectedTabId = jReport.cookie.packagesTabSelectedId();
			if ((selectedTabId == '') || (selectedTabId == null)) {
				selectedTabId = 'tstabs-1'
			}
			index = jQuery('#itemDetails a[href="#' + selectedTabId + '"]').parent().index();
			return (index == -1) ? 0 : index
		},
		gridReload : function () {
			jTemplate.helper.gridEmpty(this);
			this.gridSetting = clone(this.gridDefaultSetting);
			this.gridSetting.data = this.gridDataBind();
			this.gridSetting.totals = this.gridDataTotalBind();
			this.gridSetting.columns[6].visible = true;
			this.gridSetting.columns[8].visible = jReport.settings.devMode;
			this.gridSetting.columns[9].visible = jReport.settings.devMode;
			this.gridInit(this.gridSetting)
		},
		gridDataBind : function () {
			var currentFilterparam = jReport.page.body.leftPanel.getFilterParam();
			var xdata = [];
			try {
				for (var i = 0; i < this.data.testsuite.length; i++) {
					xdata[i] = {
						id : this.data.testsuite[i].id + getUniqueId(),
						statusbar : '',
						text : (jReport.settings.devMode && (this.data.testsuite[i].isnew == 1) ? '<span class="ico-new ui-icon float-left">&nbsp;</span>&nbsp;' : '') + '<a class="tootip link" target="_self" title="View Test Suite ' + this.data.testsuite[i].text + ' summary" href="#?id=' + this.data.testsuite[i].id + currentFilterparam + '">' + this.data.testsuite[i].text + '</a>',
						total : Number(this.data.testsuite[i].total),
						steps : Number(this.data.testsuite[i].steps),
						pots : this.getTotalOfPOTs(this.data.testsuite[i].id),
						pass : Number(this.data.testsuite[i].pass),
						passlnk : (Number(this.data.testsuite[i].pass) > 0) ? '<a class="tooltip pass link" target="_self" href="#id=' + this.data.testsuite[i].id + '&query=' + this.data.testsuite[i].id + replaceParam(currentFilterparam, 'status', 'pass') + '">' + Number(this.data.testsuite[i].pass) + '</a>' : '0',
						fail : Number(this.data.testsuite[i].fail),
						failureslnk : (this.data.testsuite[i].fail > 0) ? '<a class="tooltip fail link" target="_self" href="#id=' + this.data.testsuite[i].id + '&query=' + this.data.testsuite[i].id + replaceParam(currentFilterparam, 'status', 'fail') + '">' + Number(this.data.testsuite[i].fail) + '</a>' : '0',
						time : secondsToHms(Number(this.data.testsuite[i].time)),
						gtime : 'N/A',
						status : ((this.data.testsuite[i].total == 0) ? '<div class="ui-icon icon-fail" style="float:left">&nbsp;</div><div title="This test case is under construction" class="ui-icon tscst_ico_docu" style="float:left; margin-left:5px">&nbsp;</div>' : ('<div class="ui-icon ' + ((this.data.testsuite[i].status == 1) ? 'icon-pass' : 'icon-fail') + '">&nbsp;</div>')),
						timestamp : formatDatetime(this.data.testsuite[i].timestamp, 'HH:mm:ss TT')
					}
				}
			} catch (e) {
				/*logError(e)*/
			}
			currentFilterparam = null;
			return xdata
		},
		gridDataTotalBind : function () {
			var returnItem = {
				id : '&nbsp;',
				statusbar : '&nbsp;',
				text : '<b><i>Test Suite Total:</i></b>',
				total : 0,
				steps : 0,
				pots : 0,
				pass : 0,
				passlnk : 0,
				fail : 0,
				failureslnk : 0,
				time : 0,
				timeorg : 0,
				status : 0,
				timestamp : '&nbsp;'
			};
			try {
				returnItem.total = Number(this.data.total);
				returnItem.pass = Number(this.data.pass);
				returnItem.fail = Number(this.data.fail);
				returnItem.passlnk = Number(this.data.pass);
				returnItem.failureslnk = Number(this.data.fail);
				returnItem.pots = Number(this.data.potcount);
				returnItem.pots = this.getTotalOfPOTs(''),
				returnItem.gtime = secondsToHms(this.data.gtime);
				returnItem.time = secondsToHms(this.data.time);
				returnItem.statusbar = passFailBar(returnItem);
				returnItem.status = '<div class="noPadding ' + ((returnItem.status == 1) ? 'icon-pass' : 'icon-fail') + '">&nbsp;</div>'
					for (var i = 0; i < this.data.testsuite.length; i++) {
						returnItem.steps += Number(this.data.testsuite[i].steps);
					}
			} catch (e) {
				/*logError(e)*/
			}
			return returnItem
		},
		getTotalOfPOTs : function (testsuiteId) {
			var total = 0;
			for (var j = 0; j < this.data.testsuite.length; j++) {
				if ((testsuiteId == '') || (this.data.testsuite[j].id == testsuiteId)) {
					for (var k = 0; k < this.data.testsuite[j].testcase.length; k++) {
						for (var l = 0; l < this.data.testsuite[j].testcase[k].teststep.length; l++) {
							if (jReport.settings.devMode || (this.data.testsuite[j].testcase[k].rpt == '1')) {
								if (this.data.testsuite[j].testcase[k].teststep[l].screenshot != '') {
									total += 1
								}
							}
						}
					}
					if (testsuiteId != '') {
						break
					}
				}
			}
			return total
		},
		getTotalofSteps : function () {
			var returnValue = 0;
			for (var i = 0; i < this.data.testsuite.length; i++) {
				returnValue += Number(this.data.testsuite[i].steps)
			}
			return returnValue
		}
	},
	suite : {
		container : '',
		data : {},
		potCount : 0,
		standardOutput : '',
		errorDescription : '',
		syntaxHighlighted : false,
		load : function (container, rdata) {
			this.data = rdata;
			this.container = container;
			this.potCount = this.getTotalOfPOTs('');
			this.standardOutput = '';
			this.errorDescription = '';
			this.loadAddionalInfo();
			jTemplate.suite.syntaxHighlighted = false;
			jReport.page.loadTemplate(jTemplate.settings.bodySuite, function () {
				jQuery(jTemplate.suite.container).jqoteapp('#' + jTemplate.settings.bodySuite.id, jTemplate.suite.data);
				jTemplate.suite.onload()
			});
			rdata = null
		},
		onload : function () {
			jReport.chart('executionSummaryTable', this.data.total, this.data.pass);
			if (this.gridSetting.Grid) {
				this.gridReload()
			} else {
				this.gridSetting = clone(this.gridDefaultSetting);
				this.gridSetting.data = this.gridDataBind();
				this.gridSetting.totals = this.gridDataTotalBind();
				this.gridSetting.columns[3].visible = true;
				this.gridSetting.columns[4].visible = jReport.settings.devMode;
				this.gridSetting.columns[7].visible = jReport.settings.devMode;
				this.gridSetting.columns[8].visible = jReport.settings.devMode;
				this.gridInit(this.gridSetting)
			}
			if (jReport.settings.devMode) {
				var tabSelectedIndex = this.getTabSelectedIndex();
				jQuery('#itemDetails').tabs({
					selected : 0
				}).unbind('tabsselect').bind('tabsselect', function (event, ui) {
					var selectedTabId = jQuery(ui.panel).attr('id');
					jReport.cookie.suitesTabSelectedId(selectedTabId);
					switch (selectedTabId) {
					case 'tstabs-2':
						jTemplate.suite.syntaxHighlight();
						break;
					case 'tstabs-3':
						jTemplate.suite.syntaxHighlight();
						break;
					default:
						break
					}
					jTemplate.suite.doResize()
				});
				jQuery('#itemDetails').tabs('select', tabSelectedIndex);
			} else {
				jQuery('#itemDetails').tabs()
			}
			jTemplate.suite.doResize()
		},
		syntaxHighlight : function () {
			setTimeout(function () {
				if (!jTemplate.suite.syntaxHighlighted) {
					jQuery('#consoleOutputDynamic').ajaxMask();
					if (jTemplate.suite.standardOutput != '') {
						jQuery('#consoleOutputDynamic pre').snippet('enc', {
							style : 'matlab',
							clipboard : jReport.settings.zeroclipboard,
							transparent : true,
							showNum : true
						})
					}
					if (jTemplate.suite.errorDescription != '') {
						jQuery('#stackTraceDynamic pre').snippet('java', {
							style : 'ide-eclipse',
							clipboard : jReport.settings.zeroclipboard,
							transparent : true,
							showNum : true
						})
					}
					jTemplate.suite.syntaxHighlighted = true;
					jQuery('#consoleOutputDynamic').ajaxMask({
						stop : true
					});
				}
			}, 3000)
		},
		loadAddionalInfo : function () {
			this.standardOutput = '';
			this.errorDescription = '';
		},
		getTabSelectedIndex : function () {
			var index;
			var selectedTabId = jReport.cookie.suitesTabSelectedId();
			if ((selectedTabId == '') || (selectedTabId == null)) {
				selectedTabId = 'tstabs-1'
			}
			if ((selectedTabId == 'tstabs-2') && (this.errorDescription == '')) {
				selectedTabId = 'tstabs-1'
			}
			index = jQuery('#itemDetails a[href="#' + selectedTabId + '"]').parent().index();
			return index
		},
		doResize : function () {
			try {
				jQuery(this.container).layout({
					resize : false,
					type : 'border',
					vgap : 0,
					hgap : 0
				});
			} catch (e) {}
			var resizeGrid = !jReport.settings.devMode;
			if (jReport.settings.devMode) {
				var tabSelectedIndex = this.getTabSelectedIndex();
				if (tabSelectedIndex == 0) {
					resizeGrid = true
				} else {
					setTimeout(function () {
						jTemplate.helper.dataPanelResize(jTemplate.suite.gridDefaultSetting.container)
					}, 1000)
				}
			}
			if (resizeGrid) {
				this.gridResize()
			}
			resizeGrid = null
		},
		gridSetting : {},
		gridDefaultSetting : {
			id : 'gridPanel_testsuite_' + getUniqueId(),
			container : '#gridPanel',
			options : {
				session : false,
				rowCssClasses : function (item) {
					return ((item.rpt == 'Disabled') ? 'ignore' : '')
				}
			},
			columns : [{
					id : 'status',
					field : 'status',
					name : 'Status',
					sortable : true,
					resizable : false,
					minWidth : 65,
					width : 65,
					visible : true
				}, {
					id : 'text',
					field : 'text',
					name : 'Test Case',
					filter : 'text',
					sortable : true,
					minWidth : 200,
					width : 400,
					visible : true
				}, {
					id : 'statusbar',
					field : 'statusbar',
					name : 'Pass/Fail',
					visible : true,
					resizable : false,
					minWidth : 120,
					width : 120,
					formatter : Slick.Formatters.passFailBar
				}, {
					id : 'total',
					field : 'total',
					name : 'Steps',
					minWidth : 50,
					width : 50,
					sortable : true,
					visible : true,
					cssClass : 'alignRight'
				}, {
					id : 'pots',
					field : 'pots',
					name : 'POTs',
					minWidth : 50,
					width : 50,
					sortable : true,
					visible : false,
					cssClass : 'alignRight'
				}, {
					id : 'passlnk',
					field : 'passlnk',
					name : 'Pass',
					minWidth : 50,
					width : 50,
					sortable : true,
					visible : false,
					cssClass : 'alignRight pass'
				}, {
					id : 'failureslnk',
					field : 'failureslnk',
					name : 'Fail',
					sortable : true,
					minWidth : 50,
					width : 50,
					visible : false,
					cssClass : 'alignRight fail'
				}, {
					id : 'timestamp',
					field : 'timestamp',
					name : 'Start At',
					sortable : true,
					minWidth : 80,
					width : 80,
					visible : true,
					cssClass : 'alignRight'
				}, {
					id : 'time',
					field : 'time',
					name : 'RC Exec Time',
					sortable : true,
					minWidth : 80,
					width : 80,
					visible : false,
					cssClass : 'alignRight'
				}, {
					id : 'report',
					field : 'rpt',
					name : 'Report enable',
					sortable : false,
					visible : false
				}
			],
			totals : null,
			data : null
		},
		gridInit : function (g) {
			var defaults = {
				session : false,
				grid : {
					defaultMinWidth : 68,
					forceFitColumns : (g.columns.length <= 12),
					rowHeight : 21,
					showTotalsHeader : false,
					showTotalsFooter : true,
					syncColumnCellResize : true,
					autoContainerHeight : true,
					editable : true,
					rowCssClasses : g.options.rowCssClasses
				},
				columnPicker : {
					fadeSpeed : 150,
					showAutoResize : false,
					showSyncResize : false
				},
				view : {
					showColumnPresets : true,
					hideTotalsOnFilter : false,
					updateTotalsOnFilter : true
				}
			};
			jTemplate.helper.gridColumnInit(g, defaults);
			defaults = null;
		},
		gridResize : function () {
			setTimeout(function () {
				jTemplate.helper.gridPanelResize(jTemplate.suite.gridDefaultSetting.container);
				jTemplate.suite.gridSetting.Grid.resizeGrid()
			}, 50)
		},
		gridReload : function () {
			jTemplate.helper.gridEmpty(this);
			this.gridSetting = clone(this.gridDefaultSetting);
			this.gridSetting.data = this.gridDataBind();
			this.gridSetting.totals = this.gridDataTotalBind();
			this.gridSetting.columns[3].visible = true;
			this.gridSetting.columns[4].visible = jReport.settings.devMode;
			this.gridSetting.columns[7].visible = jReport.settings.devMode;
			this.gridSetting.columns[8].visible = jReport.settings.devMode;
			this.gridInit(this.gridSetting)
		},
		gridDataBind : function () {
			var xdata = [];
			var c = 0;
			var currentFilterparam = jReport.page.body.leftPanel.getFilterParam();
			for (var i = 0; i < this.data.testcase.length; i++) {
				if ((this.data.testcase[i].rpt == 1) || (jReport.settings.devMode)) {
					xdata[c] = {
						id : this.data.testcase[i].id + getUniqueId(),
						statusbar : '',
						text : ((jReport.settings.devMode && (this.data.testcase[i].isnew == 1)) ? '<span class="ico-new ui-icon float-left">&nbsp;</span>&nbsp;' : '') + '<a class="tootip link' + ((this.data.testcase[i].rpt == 0) ? ' ignore' : '') + '" target="_self" title="View Test Case ' + this.data.testcase[i].text + ' summary" href="#?id=' + this.data.testcase[i].id + currentFilterparam + '">' + this.data.testcase[i].text + '</a>',
						total : Number(this.data.testcase[i].total),
						pots : this.getTotalOfPOTs(this.data.testcase[i].id),
						pass : Number(this.data.testcase[i].pass),
						passlnk : (Number(this.data.testcase[i].pass) > 0) ? '<a class="tootip link pass' + ((this.data.testcase[i].rpt == 0) ? ' ignore' : '') + '" target="_self" title="View Test Case ' + this.data.testcase[i].text + ' summary" href="#?id=' + this.data.testcase[i].id + currentFilterparam + '">' + Number(this.data.testcase[i].pass) + '</a>' : '0',
						fail : Number(this.data.testcase[i].fail),
						failureslnk : (this.data.testcase[i].fail > 0) ? '<a class="tootip link fail' + ((this.data.testcase[i].rpt == 0) ? ' ignore' : '') + '" target="_self" title="View Test Case ' + this.data.testcase[i].text + ' summary" href="#?id=' + this.data.testcase[i].id + currentFilterparam + '">' + Number(this.data.testcase[i].fail) + '</a>' : '0',
						time : secondsToHms(parseFloat(this.data.testcase[i].time)),
						status : '<div class="ui-icon ' + ((this.data.testcase[i].status == 1) ? 'icon-pass' : 'icon-fail') + '">&nbsp;</div>',
						timestamp : formatDatetime(this.data.testcase[i].timestamp, 'HH:mm:ss TT'),
						rpt : ((this.data.testcase[i].rpt == 0) ? 'Disabled' : 'Enabled')
					};
					c += 1
				}
			}
			c = null;
			return xdata
		},
		gridDataTotalBind : function () {
			var returnItem = {
				id : '&nbsp;',
				statusbar : '&nbsp;',
				text : '<b><i>Test Case Total:</i></b>',
				total : 0,
				pots : 0,
				pass : 0,
				passlnk : 0,
				fail : 0,
				failureslnk : 0,
				time : 0,
				timeorg : 0,
				status : 0,
				type : '&nbsp;',
				timestamp : '&nbsp;',
				rpt : '&nbsp;'
			};
			for (var i = 0; i < this.data.testcase.length; i++) {
				if ((this.data.testcase[i].rpt == 1) || (jReport.settings.devMode)) {
					returnItem.total += Number(this.data.testcase[i].total);
					returnItem.pass += Number(this.data.testcase[i].pass);
					returnItem.passlnk += Number(this.data.testcase[i].pass);
					returnItem.fail += Number(this.data.testcase[i].fail);
					returnItem.failureslnk += Number(this.data.testcase[i].fail);
					returnItem.status += Number(this.data.testcase[i].status);
					returnItem.time += parseFloat(this.data.testcase[i].time);
				}
			}
			returnItem.pots = this.potCount;
			returnItem.time = secondsToHms(returnItem.time);
			returnItem.statusbar = passFailBar(returnItem);
			returnItem.status = '<div class="noPadding ' + ((returnItem.status == 1) ? 'icon-pass' : 'icon-fail') + '">&nbsp;</div>';
			return returnItem
		},
		getTotalOfPOTs : function (testcaseId) {
			var total = 0;
			for (var i = 0; i < this.data.testcase.length; i++) {
				if ((testcaseId == '') || (this.data.testcase[i].id == testcaseId)) {
					for (var j = 0; j < this.data.testcase[i].teststep.length; j++) {
						if (this.data.testcase[i].teststep[j].screenshot != '') {
							total += 1
						}
					}
					if (testcaseId != '') {
						break
					}
				}
			}
			return total
		}
	},
	testcase : {
		container : '',
		data : {},
		syntaxHighlighted : false,
		load : function (container, rdata) {
			this.data = rdata;
			this.container = container;
			jTemplate.testcase.syntaxHighlighted = false;
			jReport.page.loadTemplate(jTemplate.settings.bodyTestcase, function () {
				jQuery(jTemplate.testcase.container).jqoteapp('#' + jTemplate.settings.bodyTestcase.id, jTemplate.testcase.data);
				jTemplate.testcase.onload()
			});
			rdata = null
		},
		onload : function () {
			jReport.chart('executionSummaryTable', this.data.total, this.data.pass);
			if (this.gridSetting.Grid) {
				this.gridReload()
			} else {
				this.gridSetting = clone(this.gridDefaultSetting);
				this.gridSetting.data = this.gridDataBind();
				this.gridInit(this.gridSetting)
			}
			this.gridResize();
			if (jReport.settings.devMode) {
				var tabSelectedIndex = this.getTabSelectedIndex();
				jQuery('#itemDetails').tabs({
					selected : 0
				}).unbind('tabsselect').bind('tabsselect', function (event, ui) {
					var selectedTabId = jQuery(ui.panel).attr('id');
					jReport.cookie.testcaseTabSelectedId(selectedTabId);
					switch (selectedTabId) {
					case 'tabs-2':
						jTemplate.testcase.syntaxHighlight();
						break;
					case 'tabs-3':
						jTemplate.testcase.syntaxHighlight();
						break;
					default:
						break
					}
					jTemplate.testcase.doResize()
				});
				jQuery('#itemDetails').tabs('select', tabSelectedIndex);
			} else {
				jQuery('#itemDetails').tabs()
			};
			jTemplate.testcase.doResize()
		},
		togglePanel : function (obj) {
			var tgglObj = jQuery(obj).find(">:first-child");
			if (tgglObj.attr('class').indexOf('ui-icon-carat-1-s') > -1) {
				tgglObj.removeClass('ui-icon-carat-1-n');
				tgglObj.removeClass('ui-icon-carat-1-s');
				tgglObj.addClass('ui-icon-carat-1-n');
				/*Show details*/
				jQuery('#' + obj.id + '-details').fadeIn()
			} else {
				tgglObj.removeClass('ui-icon-carat-1-n');
				tgglObj.removeClass('ui-icon-carat-1-s');
				tgglObj.addClass('ui-icon-carat-1-s');
				jQuery('#' + obj.id + '-details').fadeOut('fast')
			}
			tgglObj = null;
		},
		getTabSelectedId : function () {
			return jReport.cookie.testcaseTabSelectedId();
		},
		syntaxHighlight : function () {
			setTimeout(function () {
				if (!jTemplate.testcase.syntaxHighlighted) {
					if (jTemplate.testcase.standardOutput != '') {
						jQuery('#standardOutput pre').snippet('enc', {
							style : "ide-eclipse",
							clipboard : jReport.settings.zeroclipboard,
							transparent : true,
							showNum : true
						})
					}
					if (jTemplate.testcase.errorDescription != '') {
						jQuery('#stackTraceDynamic pre').snippet('java', {
							style : "ide-eclipse",
							clipboard : jReport.settings.zeroclipboard,
							transparent : true,
							showNum : true
						})
					}
					jTemplate.testcase.syntaxHighlighted = true
				}
			}, 500)
		},
		getTabSelectedIndex : function () {
			var index;
			var selectedTabId = jReport.cookie.testcaseTabSelectedId();
			if ((selectedTabId == '') || (selectedTabId == null)) {
				selectedTabId = '#tabs-1'
			} else if ((selectedTabId == '#tabs-2') && (this.data.error.desc == '')) {
				selectedTabId = '#tabs-1'
			}
			index = jQuery('#itemDetails a[href="#' + selectedTabId + '"]').parent().index();
			return (index == -1) ? 0 : index
		},
		doResize : function () {
			try {
				jQuery(this.container).layout({
					resize : false,
					type : 'border',
					vgap : 0,
					hgap : 0
				});
			} catch (e) {}
			var resizeGrid = !jReport.settings.devMode;
			if (jReport.settings.devMode) {
				var tabSelectedIndex = this.getTabSelectedIndex();
				if (tabSelectedIndex == 0) {
					resizeGrid = true
				} else {
					jTemplate.helper.dataPanelResize(this.gridDefaultSetting.container)
				}
			}
			if (resizeGrid) {
				this.gridResize()
			}
			resizeGrid = null;
		},
		gridSetting : {},
		gridDefaultSetting : {
			id : 'gridPanel_testcase_' + getUniqueId(),
			container : '#gridPanel',
			options : {
				session : false
			},
			columns : [{
					id : 'status',
					field : 'status',
					name : 'Status',
					sortable : true,
					minWidth : 16,
					width : 16,
					visible : true
				}, {
					id : 'text',
					field : 'text',
					name : 'Step',
					filter : 'text',
					sortable : true,
					minWidth : 60,
					width : 40,
					visible : true
				}, {
					id : 'action',
					field : 'action',
					name : 'Action',
					filter : 'text',
					width : 250,
					sortable : true,
					visible : true
				}, {
					id : 'label',
					field : 'label',
					name : 'Label',
					filter : 'text',
					minWidth : 60,
					width : 220,
					sortable : true,
					visible : true
				}, {
					id : 'value',
					field : 'value',
					name : 'Expected value',
					filter : 'text',
					minWidth : 60,
					width : 150,
					sortable : true,
					visible : true
				}, {
					id : 'actualvalue',
					field : 'actualvalue',
					name : 'Actual value',
					filter : 'text',
					minWidth : 60,
					width : 150,
					sortable : true,
					visible : true
				}, {
					id : 'screenshot',
					field : 'screenshot',
					name : 'POT',
					sortable : false,
					visible : true
				}
			],
			data : null
		},
		gridInit : function (g) {
			var defaults = {
				session : false,
				grid : {
					defaultMinWidth : 68,
					forceFitColumns : (g.columns.length <= 12),
					rowHeight : 21,
					showTotalsHeader : false,
					showTotalsFooter : false,
					syncColumnCellResize : true,
					autoContainerHeight : true,
					editable : false,
					rowCssClasses : g.options.rowCssClasses
				},
				columnPicker : {
					fadeSpeed : 150,
					showAutoResize : false,
					showSyncResize : false
				},
				view : {
					showColumnPresets : true,
					hideTotalsOnFilter : false,
					updateTotalsOnFilter : false
				}
			};
			jTemplate.helper.gridColumnInit(g, defaults);
			defaults = null
		},
		gridResize : function () {
			setTimeout(function () {
				jTemplate.helper.gridPanelResize(jTemplate.testcase.gridDefaultSetting.container);
				jTemplate.testcase.gridSetting.Grid.resizeGrid()
			}, 50)
		},
		gridReload : function () {
			jTemplate.helper.gridEmpty(this);
			this.gridSetting = clone(this.gridDefaultSetting);
			this.gridSetting.data = this.gridDataBind();
			this.gridInit(this.gridSetting)
		},
		gridDataBind : function () {
			var xdata = [];
			var currentFilterparam = jReport.page.body.leftPanel.getFilterParam();
			try {
				for (var i = 0; i < this.data.teststep.length; i++) {
					xdata[i] = {
						id : this.data.teststep[i].id + getUniqueid(),
						text : '<a class="tootip link" target="_self" title="View Test Step ' + this.data.teststep[i].text + ' summary" href="#?id=' + this.data.teststep[i].id + currentFilterparam + '">' + this.data.teststep[i].text + '</a>',
						label : this.data.teststep[i].label,
						action : this.getActionLabel(this.data.teststep[i]),
						value : this.data.teststep[i].value,
						actualvalue : this.data.teststep[i].actualvalue,
						status : '<div class="ui-icon ' + ((this.data.teststep[i].status == '1') ? 'icon-pass' : 'icon-fail') + '">&nbsp;</div>',
						screenshot : this.getPOTLink(this.data.teststep[i].text, this.data.teststep[i].screenshot)
					}
				}
			} catch (e) {
				/*logError(e)*/
			}
			currentFilterparam = null;
			return xdata
		},
		getActionLabel : function (step) {
			var iconCls = jReport.utils.getStepIcon(step.action);
			var action = step.action || 'n/a';
			if (action != 'n/a') {
				return '<span class="ui-icon '
				 + iconCls
				 + '_ico_docu grd-rowimg"></span><span class="ui-icon grd-txt"> '
				 + jReport.utils.getStepAction(step)
				 + '</span>'
			}
			return '<span class="ui-icon ' + iconCls + '_ico_docu grd-rowimg"></span><span class="ui-icon grd-txt"> ' + action + '</span>'
		},
		getPOTLink : function (steptext, screenshot) {
			if (screenshot != '') {
				return '<a class="tooltip" ititle="' + steptext + ' screenshot" target="_blank" href="' + screenshot + '"><span class="ui-icon ui-icon-image grd-rowimg"></span><span class="ui-icon grd-txt"> View</span></a>'
			} else {
				return '<span class="ui-icon ui-icon-cancel grd-rowimg"></span>'
			}
		},
		getTotalOfPOTs : function () {
			var total = 0;
			for (var j = 0; j < this.data.teststep.length; j++) {
				if (this.data.teststep[j].screenshot != '') {
					total += 1
				}
			}
			return total
		}
	},
	step : {
		container : '',
		data : {},
		load : function (container, rdata) {
			this.data = rdata;
			this.container = container;
			jReport.page.loadTemplate(jTemplate.settings.bodyStep, function () {
				jQuery(jTemplate.step.container).jqoteapp('#' + jTemplate.settings.bodyStep.id, jTemplate.step.data);
				jTemplate.step.onload();
			});

			rdata = null
		},
		onload : function () {
			jQuery("pre.codestyle").snippet("java", {
				style : "ide-eclipse",
				clipboard : jReport.settings.zeroclipboard,
				transparent : true,
				showNum : true
			});
			jQuery(jTemplate.step.container + ' #itemdetails .togglepanelIcon').unbind('click').bind('click', function (event, ui) {
				var parentId = this.parentNode.id;
				var currentStatus = jReport.cookie.panelStepShownId();
				jTemplate.step.togglePanel(this.parentNode);

				currentStatus = replaceAll(currentStatus, parentId, '');
				currentStatus = replaceAll(currentStatus, '  ', ' ');
				if (jQuery('#' + parentId + '-details').css('display') != 'none') {
					currentStatus += ' ' + parentId;
				}
				currentStatus = replaceAll(currentStatus, '  ', ' ');
				jReport.cookie.panelStepShownId(currentStatus);
			});
			jTemplate.step.hideAllPanels();

			if (jReport.cookie.panelStepShownId() == '') {
				jReport.cookie.panelStepShownId('screenshot');
			}
			try {
				jReport.cookie.panelStepShownId().split(' ').forEach(function (objId) {
					if (objId != '') {
						jTemplate.step.showPanel(objId);
					}
				});
			} catch (e) {}

			this.doResize()
		},
		showPanel : function (objectId) {
			/*Show object panel*/
			if (jQuery('#' + objectId).length) {
				var tgglObj = jQuery(this.container + ' #' + objectId).find(">:first-child");
				tgglObj.removeClass('ui-icon-carat-1-n');
				tgglObj.removeClass('ui-icon-carat-1-s');
				tgglObj.addClass('ui-icon-carat-1-n');
				/*Show details*/
				jQuery('#' + objectId + '-details').show();
			}
		},
		hideAllPanels : function () {
			jQuery(this.container + ' #itemdetails .togglepanelIcon').each(function (index) {
				var tgglObj = jQuery(this.parentNode).find(">:first-child");
				tgglObj.removeClass('ui-icon-carat-1-n');
				tgglObj.removeClass('ui-icon-carat-1-s');
				tgglObj.addClass('ui-icon-carat-1-s');
				jQuery('#' + this.parentNode.id + '-details').hide()
			});
		},
		togglePanel : function (obj) {
			var tgglObj = jQuery(obj).find(">:first-child");
			if (tgglObj.attr('class').indexOf('ui-icon-carat-1-s') > -1) {
				tgglObj.removeClass('ui-icon-carat-1-n');
				tgglObj.removeClass('ui-icon-carat-1-s');
				tgglObj.addClass('ui-icon-carat-1-n');
				/*Show details*/
				jQuery('#' + obj.id + '-details').show();
			} else {
				tgglObj.removeClass('ui-icon-carat-1-n');
				tgglObj.removeClass('ui-icon-carat-1-s');
				tgglObj.addClass('ui-icon-carat-1-s');
				jQuery('#' + obj.id + '-details').hide();
			}
		},
		getPOTLink : function (pot) {
			return jReport.potFolder + '/' + pot
		},
		doResize : function () {
			var summaryObj = jQuery('#summaryPanel .summary3');
			jQuery('#summaryPanel').css({
				height : (summaryObj.outerHeight() + summaryObj.position().top + 5) + 'px'
			});
			try {
				jQuery(this.container).layout({
					resize : false,
					type : 'border',
					vgap : 0,
					hgap : 0
				});
			} catch (e) {}
			jTemplate.helper.dataPanelResize('#csvPanel')
		},
		getErrorMessage : function (stack) {
			var msgIndex = stack.indexOf(jReport.error.descSeparate);
			if (msgIndex > -1) {
				return jQuery.trim(stack.substring(0, msgIndex));
			}
			return ''
		}
	},
	pageNotFound : {
		container : '',
		data : {},
		load : function (container, rdata) {
			this.data = rdata;
			this.container = container;
			jReport.page.loadTemplate(jTemplate.settings.bodyPageNotFound, function () {
				jQuery(jTemplate.pageNotFound.container).append(jQuery('#' + jTemplate.settings.bodyPageNotFound.id).jqote());
				jTemplate.pageNotFound.onload()
			});
			rdata = null
		},
		onload : function () {
			//TODO:
		},
		doResize : function () {}
	}
};
jQuery(document).ready(function () {
	jReport.setup();
	/*Holiday*/
	setTimeout(function () {
		if (jReport.settings.devMode) {
			if (jReport.utils.isXmas()) {
				jQuery('body').addClass('xmas');
				var isnow = new iSnow();
				isnow.start(true);
			} else if (jReport.utils.isTet()) {}
		}
	}, 500)
});
