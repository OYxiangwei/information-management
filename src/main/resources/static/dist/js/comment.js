$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/comments/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'commentId', index: 'commentId', width: 50, key: true, hidden: true},
            {label: '评论内容', name: 'commentBody', index: 'commentBody', width: 120},
            {label: '评论时间', name: 'createTime', index: 'createTime', width: 60},
            {label: '评论人名称', name: 'commentator', index: 'commentator', width: 60},
            {label: '状态', name: 'commentStatus', index: 'commentStatus', width: 60, formatter: statusFormatter}
        ],
        height: 700,
        rowNum: 10,
        rowList: [10, 20, 50],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    function statusFormatter(cellvalue) {
        if (cellvalue == 0) {
            return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\" style=\"width: 50%;\">待审核</button>";
        }
        else if (cellvalue == 1) {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 50%;\">已审核</button>";
        }
    }

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });

});

/**
 * jqGrid重新加载
 */
function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

/**
 * 批量审核
 */
function checkDoneComments() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认审核通过吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/comments/checkDone",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.resultCode == 200) {
                            swal("审核成功", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal(r.message, {
                                icon: "error",
                            });
                        }
                    }
                });
            }
        }
    );
}

/**
 * 批量删除
 */
function deleteComments() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认删除这些评论吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/comments/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.resultCode == 200) {
                            swal("删除成功", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal(r.message, {
                                icon: "error",
                            });
                        }
                    }
                });
            }
        }
    );
}
$('#commentSubmit').click(function () {
    var newsId = $('#newsId').val();
    var verifyCode = $('#verifyCode').val();
    var commentator = $('#commentator').val();
    var commentBody = $('#commentBody').val();
    if (isNull(newsId)) {
        swal("参数异常", {
            icon: "warning",
        });
        return;
    }
    if (isNull(verifyCode)) {
        swal("请输入验证码", {
            icon: "warning",
        });
        return;
    }
    if (!validCN_ENString2_100(commentator)) {
        swal("请输入符合规范的名称(不要输入特殊字符)", {
            icon: "warning",
        });
        return;
    }
    if (!validCN_ENString2_100(commentBody)) {
        swal("请输入符合规范的评论内容(不要输入特殊字符)", {
            icon: "warning",
        });
        return;
    }
    var data = {
        "newsId": newsId, "verifyCode": verifyCode, "commentator": commentator,"commentBody": commentBody
    };
    console.log(data);
    $.ajax({
        type: 'POST',//方法类型
        url: '/news/comment',
        data: data,
        success: function (result) {
            if (result.resultCode == 200) {
                swal("保存成功", {
                    icon: "success",
                });
                $('#commentBody').val('');
                $('#verifyCode').val('');
            }
            else {
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });
});