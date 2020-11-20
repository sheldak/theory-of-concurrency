function printAsync(s, cb) {
    var delay = Math.floor((Math.random()*1000)+500);
    setTimeout(function() {
        console.log(s);
        if (cb) cb();
    }, delay);
}
 
function task1(cb) {
    printAsync("1", function() {
        task2(cb);
    });
}
 
function task2(cb) {
    printAsync("2", function() {
        task3(cb);
    });
}
 
function task3(cb) {
    printAsync("3", cb);
}

function loop(n) {
    let tasks = [
        function() {
            task1(function() {
                console.log('done!');
            })
        }
    ];

    for (let i=1; i<n; i++) {
        tasks.push(function() {
            task1(function() {
                tasks[i-1]()
            })
        })
    }

    tasks[n-1]();
}
 
loop(4);