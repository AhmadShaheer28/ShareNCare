// config should be imported before importing any other file
const config = require('./config/config');
const app = require('./config/express');

if (!module.parent) {
    app.listen(config.port, () => {
        console.info(`server started on port ${config.port} (${config.env})`);
    });
}

module.exports = app;
