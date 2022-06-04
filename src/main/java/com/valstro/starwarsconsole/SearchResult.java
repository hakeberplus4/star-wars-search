package com.valstro.starwarsconsole;

import lombok.Data;

@Data
public class SearchResult {

    int page;           // Message index. If this matches resultCount, it is the last message in a sequence!
    int resultCount;    // total number of results to be sent to client
    String name;        // name matched against query
    String films;       // array of comma-separated strings, showing a character's filmography
    String error;       // error text will always be populated for errors
    // if a message represents an error, page & resultCount will always be -1
}
