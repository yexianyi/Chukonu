window.App2 = {
  mount(selector) {
    this.$el = $(selector);
    this.$el.html(`
      <div class="app2">
        <h4>子应用 2（真正微前端模块）</h4>
        <button id="btn2">测试按钮</button>
      </div>
      <style>
        .app2 { background: #fff7e6; padding: 20px; border-radius: 8px; }
        #btn2 { background: #fa8c16; color: white; border: none; padding: 6px 12px; }
      </style>
    `);

    $("#btn2").off("click").on("click", () => {
      alert("我是子应用2，和主应用、子应用1完全无关");
    });
  },

  unmount() {
    this.$el.empty();
    $("#btn2").off("click");
  }
};