<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>

<div style="position: absolute; overflow: hidden; z-index: 1010; outline: 0px none; height: auto; width: 511px; top: 173px; left: 367px;"
	class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable" tabindex="-1" role="dialog"
	aria-labelledby="ui-dialog-title-dialog">
	<div class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix" unselectable="on" style="-moz-user-select: none;">
		<span class="ui-dialog-title" id="ui-dialog-title-dialog" unselectable="on" style="-moz-user-select: none;">${dialog.title}</span>
		<a href="#" class="ui-dialog-titlebar-close ui-corner-all" role="button" unselectable="on" style="-moz-user-select: none;">
			<span class="ui-icon ui-icon-closethick" unselectable="on" style="-moz-user-select: none;">close</span>
		</a>
	</div>
	<div id="dialog" class="ui-dialog-content ui-widget-content" style="height: ${dialog.height}px; min-height: 42px; width: auto;">
		${dialog.context}</div>
	<div class="ui-resizable-handle ui-resizable-n" unselectable="on" style="-moz-user-select: none;"></div>
	<div class="ui-resizable-handle ui-resizable-e" unselectable="on" style="-moz-user-select: none;"></div>
	<div class="ui-resizable-handle ui-resizable-s" unselectable="on" style="-moz-user-select: none;"></div>
	<div class="ui-resizable-handle ui-resizable-w" unselectable="on" style="-moz-user-select: none;"></div>
	<div class="ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se ui-icon-grip-diagonal-se"
		style="z-index: 1001; -moz-user-select: none;" unselectable="on"></div>
	<div class="ui-resizable-handle ui-resizable-sw" style="z-index: 1002; -moz-user-select: none;" unselectable="on"></div>
	<div class="ui-resizable-handle ui-resizable-ne" style="z-index: 1003; -moz-user-select: none;" unselectable="on"></div>
	<div class="ui-resizable-handle ui-resizable-nw" style="z-index: 1004; -moz-user-select: none;" unselectable="on"></div>
	<div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix">
		<button id="affirmBut" type="button" class="ui-state-default ui-corner-all">确定</button>
		<button id="cancelBut" type="button" class="ui-state-default ui-corner-all">取消</button>
	</div>
</div>