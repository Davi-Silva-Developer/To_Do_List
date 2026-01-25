// Pacote de classes utilitárias
package br.com.davisilva.todolist.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class Utils {

        // Copia propriedades do source para o target
        // Ignora campos que estão null no source
    public static void copyNOnNullProperties(Object source, Object target){
        BeanUtils.copyProperties(source, target, getNullProperttuNames(source));
    }

        // Retorna os nomes das propriedades que estão null
    public static String[] getNullProperttuNames(Object source){

        // Permite acessar propriedades dinamicamente
        BeanWrapper src = new BeanWrapperImpl(source);

        // Lista todas as propriedades do objeto
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
            // Armazena nomes das propriedades nulas
        Set<String> emptyNames = new HashSet<>();

        // Obtém o valor da propriedade
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());

            // Adiciona o nome se o valor for null
            if (srcValue == null) {
                emptyNames.add(pd.getName());

            }
        }
        // Converte o Set em array
        return emptyNames.toArray(new String[0]);

    }
}