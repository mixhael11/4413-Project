

function fetchAuctions() {
    const query = $("#searchQuery").val();

    console.log("Search query:", query);  

    $.ajax({
        url: "AuctionUpdateServlet", 
        method: "GET",
        data: { query: query },  
        dataType: "json",
        success: function(data) {
            const { activeAuctions, notifications, auctionEnded, auctionId, auctionStatus} = data
            
            if(auctionStatus){
                $('#auctionStatus').text(auctionStatus);
            }
            // Update the auction table
            if ($("#auction-table-body").length > 0) {
                updateAuctionTable(activeAuctions);
            }

            notifications.forEach(notification => {
                const auctionId = notification.auctionId || "Unknown";
                const bidderId = notification.bidderId || "Unknown";
                const itemName = notification.itemName || "Unknown";

                console.log(`Auction ID: ${auctionId}, Bidder ID: ${bidderId}`);
            });
            if(auctionEnded){
                console.log("forwarding");
                window.location.href = "BiddingEndServlet?auctionId=" + auctionId;
            }
        },
        error: function(xhr, status, error) {
            console.error("Failed to fetch auctions:", status, error);
            $("#auction-table-body").html("<tr><td colspan='5'>Failed to load auctions</td></tr>");
        }
    });
}

function updateAuctionTable(auctions) {
    const tableBody = $("#auction-table-body");

    const selectedAuctionId = $("input[name='selectedAuctionId']:checked").val();

    tableBody.empty();

    auctions.forEach(function(auction) {

        const isChecked = auction.id === selectedAuctionId ? "checked" : "";
        const remaining = auction.auctionType === 'Forward' 
            ? (auction.remaining || "N/A") + " mins" 
            : "NOW";

        const row = "<tr>" +
            "<td>" + (auction.itemName || "N/A") + "</td>" +
            "<td>" + (auction.currentBid || "N/A") + "</td>" +
            "<td>" + (auction.auctionType || "N/A") + "</td>" +
            "<td>" + remaining + "</td>" +
            "<td>" +
               '<input type="radio" name="selectedAuctionId" value="' + auction.id + '" ' + isChecked + ' required>' +
            "</td>" +
            "</tr>";

        tableBody.append(row);
    });

    if (auctions.length === 0) {
        tableBody.html("<tr><td colspan='5'>No auctions available</td></tr>");
    }

    if (selectedAuctionId) {
        $("input[name='selectedAuctionId'][value='" + selectedAuctionId + "']").prop('checked', true);
    }
}

$(document).ready(function() {
    fetchAuctions();
    setInterval(fetchAuctions, 1000); 
});
