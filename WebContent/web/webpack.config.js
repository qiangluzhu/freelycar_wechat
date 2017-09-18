// const ExtractTextPlugin = require('extract-text-webpack-plugin')
// const HtmlWebpackPlugin = require('html-webpack-plugin')
// const WebpackMd5Hash = require('webpack-md5-hash')
const webpack = require('webpack')
module.exports = function (webpackConfig, env) {
    // 对roadhog默认配置进行操作，比如：
    if (env === 'production') {
        // webpackConfig.plugins.push('...')
        webpackConfig.entry.vendor=['react',"react-router-dom",'react-redux',"react-router-redux"]
        webpackConfig.plugins.push(new webpack.optimize.CommonsChunkPlugin('vendor','vendor.js'))
    }
    return webpackConfig;
}