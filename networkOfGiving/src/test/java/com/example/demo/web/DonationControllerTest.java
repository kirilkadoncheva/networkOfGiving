package com.example.demo.web;

import com.example.demo.data.Charity;
import com.example.demo.data.Donation;
import com.example.demo.impl.CharityManager;
import com.example.demo.impl.DonationManager;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class DonationControllerTest {
     Donation d1;
     Donation d2;

    @Mock
    private DonationManager donationManager;

    @InjectMocks
    private DonationController donationController;

    @BeforeEach
    void init_mocks() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void setUp()
    {
            d1 = new Donation("user1",2,100,new Date());
            d2 = new Donation("user2",3,200,new Date());

    }

    @Test
    public void getAllDonationsWhenThereAreNone()
    {
        Mockito.when(donationManager.getAllDonations()).thenReturn(null);
       assertNull(donationController.getAllDonations());

       Mockito.verify(donationManager,Mockito.times(1)).getAllDonations();
    }

    @Test
    public void getAllDonationsByUsernameWhenNone()
    {
        Mockito.when(donationManager.getAllDonationsByUsername("noUser")).thenReturn(null);
        assertNull(donationController.getAllDonationsByUsername("noUser"));

        Mockito.verify(donationManager,Mockito.times(1)).getAllDonationsByUsername("noUser");
    }

    @Test
    public void getAllDonationsByUsernameWhenThereIsOne()
    {
        Mockito.when(donationManager.getAllDonationsByUsername("user1")).thenReturn(Arrays.asList(d1));

        assertNotNull(donationController.getAllDonationsByUsername("user1"));
        assertEquals(donationController.getAllDonationsByUsername("user1").size(),1);

        Mockito.verify(donationManager,Mockito.times(2)).getAllDonationsByUsername("user1");
    }
}
