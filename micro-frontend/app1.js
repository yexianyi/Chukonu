// 子应用是独立模块，导出微前端生命周期
// 完全可以用 webpack 打包出来
window.App1 = {
  mount(selector) {
    this.$el = $(selector);
    this.$el.html(`
      <div class="app1">
        <h4>子应用 1（真正微前端模块）</h4>
        <button id="btn1">测试按钮</button>
      </div>
      <style>
        .app1 { background: #e6f7ff; padding: 20px; border-radius: 8px; }
        #btn1 { background: #1890ff; color: white; border: none; padding: 6px 12px; }
      </style>
    `);

    // 子应用独立逻辑
    $("#btn1").off("click").on("click", () => {
      alert("我是子应用1，独立部署、独立运行");
    });
  },

  unmount() {
    // 清空 DOM、解绑事件、销毁状态
    this.$el.empty();
    $("#btn1").off("click");
  }
};