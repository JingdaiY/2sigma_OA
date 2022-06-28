package main.java;

import java.util.*;

public class IPO {

    public static List<Integer> getResults(List<List<Integer>> bids, int totalShares) {

        //sort the input by price and timeStamp;
        Collections.sort(bids, new BidComparator());

        //group bids by price
        //using treemap here to sort by price in descending order
        Map<Integer, List<List<Integer>>> mapByPrice = new TreeMap<>(Collections.reverseOrder());

        //recort the unique user_ids
        Set<Integer> user_ids = new HashSet<>();

        //generate the mapByPrice
        for (List<Integer> bid : bids) {

            List<List<Integer>> list = mapByPrice.getOrDefault(bid.get(2), new ArrayList<>());

            list.add(bid);

            user_ids.add(bid.get(0));

            mapByPrice.put(bid.get(2), list);

        }

        //distribute shares by price in descending order
        for (int i : mapByPrice.keySet()) {

            List<List<Integer>> list = mapByPrice.get(i);

            int totalNeededShares = 0;

            for (List<Integer> bid : list) {

                totalNeededShares += bid.get(1);

            }

            //3 cases:
            //1. shares are enough for all bidders who bid with the price
            //2. shares are not enough for all bidders who bid with the price but each bidders can at least get one share
            //3. shares are not enough for everyone to get at least one share

            //for case 1 and case 2
            if (totalShares >= totalNeededShares || totalShares >= list.size()) {

                //remove the user_id from set for the bidders who get shares
                for (List<Integer> bid : list) {

                    user_ids.remove(bid.get(0));

                }

                //for case 1
                if (totalShares >= totalNeededShares) {

                    totalShares -= totalNeededShares;

                }else {
                    //case 2
                    //totalShares -> 0
                    break;

                }

            }else {
                //case 3
                //remove the user_id from set for the bidders who get shares
                int index = 0;

                while (index < totalShares) {
                    //round robin
                    user_ids.remove(list.get(index++).get(0));

                }
                //totalShares -> 0
                break;

            }

        }

        List<Integer> rs = new ArrayList<>(user_ids);

        return rs;

    }

    private static class BidComparator implements Comparator<List<Integer>> {

        @Override
        public int compare(List<Integer> a, List<Integer> b) {

            if (a.get(2) != b.get(2)) return b.get(2) - a.get(2);

            return a.get(3) - b.get(3);

        }

    }

//    public static void main(String[] args) {
//
//        int[][] test = {
//                {1,2,4,0},
//                {2,2,4,6},
//                {3,2,4,2},
//                {4,3,1,7},
//                {1,3,1,3},
//                {5,3,2,9},
//                {1,3,2,3}
//        };
//
//        List<Integer> rs = ipo(test, 13);
//
//        System.out.println(rs);
//
//    }

}
