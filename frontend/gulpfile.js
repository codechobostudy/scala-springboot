'use strict';

var gulp = require('gulp');
var fs = require('fs');

gulp.task('clean', function (cb) {
    return require('rimraf')('dist', cb);
});

gulp.task('lint', function () {
    var jshint = require('gulp-jshint');

    return gulp.src('app/scripts/**/*.js')
        .pipe(jshint())
        .pipe(jshint.reporter('default'));
});

gulp.task('font', function(){
    return gulp.src(['app/bower_components/bootstrap/fonts/**',
        'app/bower_components/fontawesome/fonts/**'])
        .pipe(gulp.dest('dist/view/fonts'))
});

gulp.task('images', function () {
    var cache = require('gulp-cache'),
        imagemin = require('gulp-imagemin');

    return gulp.src('app/images/**/*')
        .pipe(cache(imagemin({
            progressive: true,
            interlaced: true
        })))
        .pipe(gulp.dest('dist/view/images'));
});

gulp.task('less', function () {
    var less = require('gulp-less');
    return gulp.src('app/styles/*.less')
        .pipe(less())
        .pipe(gulp.dest('app/styles'));
});

gulp.task('min',['coffee', 'less'], function () {
    var uglify = require('gulp-uglify'),
        minifyCss = require('gulp-minify-css'),
        useref = require('gulp-useref'),
        gulpif = require('gulp-if'),
        assets = useref.assets();


    return gulp.src(['app/*.html'])
        .pipe(assets)
        .pipe(gulpif('*.js', uglify()))
        .pipe(gulpif('*.css', minifyCss()))
        .pipe(assets.restore())
        .pipe(useref())
        .pipe(gulp.dest('dist/view/'));
});

gulp.task('wiredep', function () {
    var wiredep = require('wiredep').stream;

    return gulp.src('app/*.html')
        .pipe(wiredep({
            directory: 'app/bower_components'
        }))
        .pipe(gulp.dest('app'));
});

gulp.task('connect', function () {
    var connect = require('connect');
    var serveStatic = require('serve-static');
    var serveIndex = require('serve-index');
    var app = connect()
        .use(require('connect-livereload')({ port: 35729 }))
        .use(serveStatic('app'))
        .use(serveIndex('app'));

    require('http').createServer(app)
        .listen(9000)
        .on('listening', function() {
            console.log('Started connect web server on http://localhost:9000.');
        });
});

gulp.task('server', ['less','connect'], function () {
    var livereload = require('gulp-livereload');

    livereload.listen();

    require('opn')('http://localhost:9000');

    gulp.watch([
        'app/*.html',
        'app/styles/**/*.css',
        'app/styles/**/*.less',
        'app/scripts/**/*.js',
        'app/scripts/**/*.coffee'
    ],['less','coffee']).on('change', livereload.changed);

    gulp.watch('bower.json', ['wiredep']);
});

gulp.task('test', function(){
    var karma = require('gulp-karma');

    return gulp.src('./foobar') // fake url
        .pipe(karma({
            configFile: '.karma.conf.js',
            action: 'run'
        }))
        .on('error', function(err) {
            console.log(err);
            this.emit('end');
        });
});

gulp.task('coffee', function(){
    var sourcemaps = require('gulp-sourcemaps');
    var coffee = require('gulp-coffee');

    return gulp.src('app/scripts/**/*.coffee')
        .pipe(sourcemaps.init())
        .pipe(coffee())
        .pipe(sourcemaps.write())
        .pipe(gulp.dest('./dest/js'));
});

gulp.task('build', ['lint', 'min', 'images', 'font']);

gulp.task('default', ['server'], function () {
    gulp.start('build');
});
