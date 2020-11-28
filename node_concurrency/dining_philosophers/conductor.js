const fs = require("fs");

var Conductor = function(maxEating) {
    this.currPlaces = maxEating;
}

var Fork = function(id) {
    this.state = 0;
    this.id = id;
    return this;
}

var acquire = function(fork, waitingTime, printWaiting, callback, stream) { 
    printWaiting(waitingTime, fork.id);

    if (fork.state == 1) {
        setTimeout(function() {
            acquire(fork, waitingTime*2, printWaiting, callback, stream);
        }, waitingTime);
    }
    else {
        fork.state = 1;

        stream.write(`f ${waitingTime*2 - 1}\n`);

        callback();
    }
}

var release = function(fork, callback, conductor) { 
    fork.state = 0;

    if (conductor) {
        conductor.currPlaces++;
    }

    callback();
}

var sit = function(waitingTime, conductor, philosopherID, callback, stream) {
    console.log("[%d]: Waiting %dms for place", philosopherID, waitingTime);

    setTimeout(function() {
        if (conductor.currPlaces > 0) {
            conductor.currPlaces--;

            stream.write(`c ${waitingTime*2 - 1}\n`);

            callback();
        } else {
            sit(waitingTime * 2, conductor, philosopherID, callback, stream);
        }
    }, waitingTime);
}

var Philosopher = function(id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id+1) % forks.length;
    return this;
}

Philosopher.prototype.startConductor = function(count, conductor, stream) {
    var id = this.id;

    var printWaiting = function(waitingTime, forkID) {
        console.log("[%d]: Waiting %dms for a fork %d", id, waitingTime, forkID);
    };

    eatLoop(this, printWaiting, count, conductor, stream)();
}

var eatLoop = function(philosopher, printWaiting, n, conductor, stream) {
    if (n == 0) {
        return function() {};
    } else {
        var fork1 = philosopher.forks[philosopher.f1];
        var fork2 = philosopher.forks[philosopher.f2];
        var id = philosopher.id;

        var nextMeals = eatLoop(philosopher, printWaiting, n-1, conductor, stream);

        var releaseSecond = function() {
            release(fork2, nextMeals, conductor);
        };

        var releaseFirst = function() {
            release(fork1, releaseSecond);
        };

        var eat = function() {
            console.log("[%d]: I am eating", id);
            releaseFirst();
        };

        var acquireSecond = function() {
            setTimeout(function() {
                acquire(fork2, 1, printWaiting, eat, stream);
            }, 1);
        };

        var acquireFirst = function() {
            console.log("[%d]: Ready for eating", id);

            setTimeout(function() {
                acquire(fork1, 1, printWaiting, acquireSecond, stream);
            }, 1);
        };

        var sitDown = function() {
            sit(1, conductor, id, acquireFirst, stream)
        }
    
        return sitDown;
    }
}


var N = 12;
var forks = [];
var philosophers = [];

var conductor = new Conductor(N-1);

for (var i = 0; i < N; i++) {
    forks.push(new Fork(i));
}

for (var i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}

var stream = fs.createWriteStream("conductor_times.txt", {flags: 'a'});
stream.write(`\nN = ${N}\n`)

for (var i = 0; i < N; i++) {
    philosophers[i].startConductor(10, conductor, stream);
}