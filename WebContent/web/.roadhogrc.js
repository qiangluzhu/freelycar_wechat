const path = require('path');
var pxtorem = require('postcss-pxtorem');
const svgSpriteDirs = [
  require.resolve('antd-mobile').replace(/warn\.js$/, ''), // antd-mobile 内置svg
  // path.resolve(__dirname, 'src/my-project-svg-foler'),  // 业务代码本地私有 svg 存放目录
];
export default {
  entry: "src/index.js",
  disableCSSModules: true,
  less: true,
  env: {
    development: {
      extraBabelPlugins: [
        "dva-hmr",
        "transform-runtime",
        ["import", { "libraryName": "antd-mobile", "style": true }]
      ],
      extraPostCSSPlugins: [
        pxtorem({
          rootValue: 100,
          propWhiteList: [],
        }),
      ]
    },
    production: {
      extraBabelPlugins: [
        "transform-runtime",
        ["import", { "libraryName": "antd-mobile", "style": true }]
      ],
      extraPostCSSPlugins: [
        pxtorem({
          rootValue: 100,
          propWhiteList: [],
        }),
      ]
    }
  },
  proxy: {
    "/api": {
      "target": "http://192.168.0.109:8080/freelycar/api/",
      "changeOrigin": true,
      "pathRewrite": { "^/api": "" }
    }
  },
  autoprefixer: {
    "browsers": [
      "iOS >= 8", "Android >= 4"
    ]
  },
  svgSpriteLoaderDirs: svgSpriteDirs,

}
