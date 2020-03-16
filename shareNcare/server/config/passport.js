const passport = require('passport');
const LocalStrategy = require('passport-local');
const JwtStrategy = require('passport-jwt').Strategy;
const ExtractJwt = require('passport-jwt').ExtractJwt;
const bcrypt = require('bcrypt');
const config = require('./config');
const sql = require('./mysql');

const localLogin = new LocalStrategy({
    usernameField: 'email'
}, async (email, password, done) => {
    var request = sql;
    const query = `SELECT *
            FROM users
            WHERE email='${email}';`;
    request.query(query, function (err, data) {
        if (err) console.log(err);
        // console.log(data[0]);
        let user = data[0];
        if (!user || !bcrypt.compareSync(password, user.password)) {
            return done(null, false, { error: 'Your login details could not be verified. Please try again.' });
        }
        delete user.password;
        done(null, user);
    });
});


const jwtLogin = new JwtStrategy({
    jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
    secretOrKey: config.jwtSecret
}, async (payload, done) => {
    var request = sql;
    const query = `SELECT * FROM users WHERE id=${payload.id};`;
    request.query(query, function (err, data) {

        if (err) console.log(err);

        let user = data[0];
        if (!user) {
            return done(null, false);
        }
        delete user.password;
        done(null, user);
    });
});

passport.use(localLogin);
passport.use(jwtLogin);


module.exports = passport;
