/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kata.sb_squashproject_mvc.services;

import java.time.LocalDateTime;
import java.util.List;
import kata.sb_squashproject_mvc.model.Match;

/**
 *
 * @author Katalin
 */
public class Services {
    
     public List<Match> orderByDate(List<Match> inputList) {

        for (int currentIndex = 0; currentIndex < inputList.size(); currentIndex++) {

            Match currentMatch = inputList.get(currentIndex);
            LocalDateTime currentDate = currentMatch.getDateOfMatch();

            for (int nextIndex = currentIndex + 1; nextIndex < inputList.size(); nextIndex++) {

                Match nextMatch = inputList.get(nextIndex);
                LocalDateTime nextDate = nextMatch.getDateOfMatch();

                if (currentDate.compareTo(nextDate) > 0) {

                    inputList.set(nextIndex, currentMatch);
                    inputList.set(currentIndex, nextMatch);
                    currentIndex = -1;
                    break;
                }
            }
        }

        return inputList;
    }
    
}
