document.addEventListener('DOMContentLoaded', function() {
    // 1. Function to Load Monthly Trend Chart Data
    function loadMonthlyTrends() {
        fetch('/analytics/api/trends')
            .then(response => {
                if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
                return response.json();
            })
            .then(data => {
                const labels = data.map(item => item.monthYear);
                const efficiencyData = data.map(item => item.averageEfficiency);
                drawMonthlyTrendChart(labels, efficiencyData);
            })
            .catch(error => console.error('Error fetching monthly trends:', error));
    }

    // 2. Function to Draw the Monthly Trend Chart
    function drawMonthlyTrendChart(labels, efficiencyData) {
        const ctx = document.getElementById('monthlyTrendChart').getContext('2d');
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Average KM/L',
                    data: efficiencyData,
                    backgroundColor: 'rgba(54, 162, 235, 0.7)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: { y: { beginAtZero: true } },
                plugins: { legend: { display: false } }
            }
        });
    }

    // 3. Function to Load Vehicle Breakdown Data
    function loadVehicleBreakdown() {
        fetch('/analytics/api/breakdown')
            .then(response => {
                if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
                return response.json();
            })
            .then(data => {
                const vehicleLabels = data.map(item => item.vehicleId);
                const efficiencyValues = data.map(item => item.averageEfficiency);
                const reportCounts = data.map(item => item.reportCount);
                drawBreakdownChart(document.getElementById('vehicleBreakdownChart'), 'Average Efficiency (KM/L)', vehicleLabels, efficiencyValues, 'bar');
                drawBreakdownChart(document.getElementById('reportCountChart'), 'Total Reports Count', vehicleLabels, reportCounts, 'doughnut');
            })
            .catch(error => console.error('Error fetching vehicle breakdown:', error));
    }

    // 4. NEW: Function to Load and Format Leaderboard Data
    // This ensures no -0.00 kg appears on the UI
    function loadLeaderboard() {
        fetch('/analytics/api/leaderboard') // Adjust endpoint if needed
            .then(response => response.json())
            .then(data => {
                const tableBody = document.querySelector('#leaderboardTable tbody');
                if(!tableBody) return;
                
                tableBody.innerHTML = '';
                data.forEach((row, index) => {
                    // Ensure the number is never negative before formatting
                    const displaySaved = Math.max(0, row.co2Saved).toFixed(2);
                    
                    const tr = `<tr>
                        <td>#${index + 1}</td>
                        <td>${row.vehicleId}</td>
                        <td>${row.totalDistance.toFixed(1)}</td>
                        <td>${displaySaved} kg</td>
                        <td><span class="badge">${row.ecoScore}</span></td>
                        <td>${row.ecoScore < 30 ? '⚠️ Improve' : '✅ Great'}</td>
                    </tr>`;
                    tableBody.innerHTML += tr;
                });
            })
            .catch(error => console.warn('Leaderboard loading handled elsewhere or endpoint missing.'));
    }
    
    function drawBreakdownChart(ctx, title, labels, data, type) {
        if(!ctx) return;
        new Chart(ctx, {
            type: type,
            data: {
                labels: labels,
                datasets: [{
                    label: title,
                    data: data,
                    backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40'],
                    hoverOffset: 4
                }]
            }
        });
    }

    // Initialize all loaders
    loadMonthlyTrends();
    loadVehicleBreakdown();
    loadLeaderboard();
	
	document.addEventListener('DOMContentLoaded', function() {
	    const downloadBtn = document.getElementById('downloadReportBtn');

	    if (downloadBtn) {
	        downloadBtn.addEventListener('click', function() {
	            // Get current date for the report
	            const now = new Date();
	            const year = now.getFullYear();
	            const month = now.getMonth() + 1; // JavaScript months are 0-11

	            // Trigger the browser to download the file from your API
	            // URL matches the @GetMapping in your EfficiencyRestController
	            window.location.href = `/api/reports/monthly/${year}/${month}`;
	        });
	    }
	});
});