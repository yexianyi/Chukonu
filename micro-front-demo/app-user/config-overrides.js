module.exports = {
  webpack: (config) => {
    config.output.library = `app-user`;
    config.output.libraryTarget = 'umd';
    return config;
  },
  devServer: (config) => {
    config.headers = {
      'Access-Control-Allow-Origin': '*',
    };
    return config;
  },
};