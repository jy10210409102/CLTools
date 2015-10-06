/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chenli.dao;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class RowLanguage implements Serializable{

    private String[] Language = new String[22];

    public RowLanguage(String chs) {
        Language[1] = chs;
    }

    public RowLanguage(String[] chsArry) {
        Language = chsArry;
    }

    public RowLanguage(String english, String chs) {
        Language[0] = english;
        Language[1] = chs;
    }

    public RowLanguage() {
    }

    public String[] getLanguage() {
        return Language;
    }

    public void setLanguage(String[] Language) {
        this.Language = Language;
    }
}
