package com.example.demo.web;

import com.example.demo.data.Charity;
import com.example.demo.impl.CharityManager;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class CharityControllerTest {

    Charity charity1;
    Charity charity2;
    Charity charity3;
    String username;

    @Mock
    private CharityManager charityManager;


    @InjectMocks
    private CharityController charityController;

    @BeforeEach
    void init_mocks() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void setUp()
    {
//        this.charityController = new CharityController(this.charityManager);
        username="user1";
        charity1=new Charity(1,username,"charity1","d1",10,0,0,0,false,"");
        charity2=new Charity(2,username,"charity2","d2",11,0,0,0,false,"");
        charity3=new Charity(3,username,"charity3","d1",13,0,0,0,false,"");

    }

    @Test
    public void getAllCharitiesWhenThereAreCharities()
    {
        Mockito.when(charityManager.getAllCharities()).thenReturn(Arrays.asList(charity1, charity2, charity3));
        assertEquals(charityController.getAllCharities().size(),3);
        Mockito.verify(charityManager, Mockito.times(1)).getAllCharities();
    }

    @Test
    public void getAllCharitiesWhenThereAreNoCharities()
    {
        Mockito.when(charityManager.getAllCharities()).thenReturn(null);
        assertNull(charityController.getAllCharities());
        Mockito.verify(charityManager, Mockito.times(1)).getAllCharities();
    }

    @Test
    public void getCharityByIdWhenThereIsNoSuchCharity()
    {
        Mockito.when(charityManager.getCharityById(4)).thenReturn(null);
        assertNull(charityController.getCharityById(4));
        Mockito.verify(charityManager, Mockito.times(1)).getCharityById(4);
    }

    @Test
    public void getCharitiesByUsernameWhenThereAreNone()
    {
        Mockito.when(charityManager.getAllCharitiesByOwner("gosho")).thenReturn(null);

        //System.out.println(charity3.getId());

        assertNull(charityController.getCharitiesByUsername("gosho"));
        Mockito.verify(charityManager, Mockito.times(1)).getAllCharitiesByOwner("gosho");
    }

    @Test
    public void getCharitiesByUsernameWhenThereAreThree()
    {
        Mockito.when(charityManager.getAllCharitiesByOwner(username)).thenReturn(Arrays.asList(charity1,charity2, charity3));

        //System.out.println(charity3.getId());

        assertEquals(charityController.getCharitiesByUsername(username).size(),3);
        Mockito.verify(charityManager, Mockito.times(1)).getAllCharitiesByOwner(username);
    }

}