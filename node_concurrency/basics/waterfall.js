var async = require('async');

let next = {
    "1": "2",
    "2": "3",
    "3": "1",
}

function printAsync(num, callback) {
    var delay = Math.floor((Math.random()*500)+250);
    setTimeout(function() {
        console.log(num);
        if (callback) 
            callback(null, num);
    }, delay);
}

function start(callback) {
    printAsync("1", callback);
}
 
function task(last_num, callback) {
    printAsync(next[last_num], callback);
}

function done(_, _) {
    printAsync("done!");
}

function loop(n) {
    let tasks = [start, task, task];

    for (let i=1; i<n; i++) {
        tasks.push(task, task, task);
    }

    async.waterfall(tasks, done)
}
 
loop(4);