$(function() {

    Morris.Line({
        element: 'morris-line-chart',
        data: [{
            period: '2015-01',
            twitter: 4.5
        }, {
            period: '2015-02',
            twitter: 4.5,
        }, {
            period: '2015-03',
            facebook: 3.4,
            twitter: 4.6
        }, {
            period: '2015-04',
            facebook: 3.6,
            twitter: 4.7
        }, {
            period: '2015-05',
            facebook: 3.5,
            twitter: 4.5
        }, {
            period: '2015-06',
            twitter: 4.6
        }, {
            period: '2015-07',
            facebook: 3.8,
            twitter: 4.7
        }, {
            period: '2015-08',
            facebook: 4.1,
            twitter: 4.8
        }],
        xkey: 'period',
        ykeys: ['twitter', 'facebook'],
        labels: ['Twitter', 'Facebook'],
        pointSize: 2,
        hideHover: 'auto',
        resize: true,
        ymax: 5,
        ymin: 1
    });


    Morris.Donut({
        element: 'morris-donut-chart',
        data: [{
            label: "Bug reporting",
            value: 30
        }, {
            label: "Sugg. new features",
            value: 12
        }, {
            label: "Other",
            value: 20
        }],
        resize: true
    });
});
