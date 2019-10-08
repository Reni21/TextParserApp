package service;

import entity.Text;

public interface TextService {
    Text deleteSubstringInEverySentence(Text src, char from, char to);
}
