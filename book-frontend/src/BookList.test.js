import React from "react";
import { render, screen, fireEvent,act } from '@testing-library/react';

import App from "./App";


describe('test BookList', () => {

  beforeEach(() =>{
  fetchMock.doMock();
}); 

   test('test BookList', async () => {

    const books =
      [
        {
          "id": "d898a142-de44-466c-8c88-9ceb2c2429d3",
          "title": "Design Pattern",
          "author": "Admin",
          "publicationYear": "1998",
          "isbn": "JCKY-JKNM-OPEL-QWEP"
        },
        {
          "id": "e6a625cc-718b-48c2-ac76-1dfdff9a531e",
          "title": "Head First Java",
          "author": "Justin Zhou",
          "publicationYear": "2015",
          "isbn": "1253-NHTY-OUIL-BVSW"
        }
      ];

    

    fetchMock.mockResponse(JSON.stringify(books));

    const { debug } = render(<App />);

    debug();

    const deleteBtns =await screen.findAllByText(/Delete/);
    const editBtns = await screen.findAllByText(/Edit/);

    expect(deleteBtns).toHaveLength(2);
    expect(editBtns).toHaveLength(2);
    debug();

    //remove one book

        //mock a book save success, and GET /books will return one book
        fetchMock.resetMocks();
        fetchMock.doMock();
        fetchMock.mockResponse(JSON.stringify({
          "id": "e6a625cc-718b-48c2-ac76-1dfdff9a531e",
          "title": "Head First Java",
          "author": "Justin Zhou",
          "publicationYear": "2015",
          "isbn": "1253-NHTY-OUIL-BVSW"
        }));


        act(() => {
          fireEvent.click(deleteBtns[0]);
        });


    //only one book in list
/*     expect(await screen.findAllByText(/Delete/)).toHaveLength(1);

    debug(); */


/*     //edit the existed one book
    fireEvent.click(editBtns.at(0))

    //go to edit page
    const editBookTitle = screen.getByText("Edit Book");
    expect(editBookTitle).toBeInTheDocument;

    const saveBtn = screen.getByText(/Save/);
    expect(saveBtn).toBeInTheDocument;

    debug();

    //change isbn
    const inputIsbn = screen.getByLabelText("isbn");
    expect(inputIsbn).toBeInTheDocument;

    inputIsbn.setAttribute("value", "1111111111111111");

    //click save
    fireEvent.click(saveBtn.at(0));

    //go back to list page, to see if the book is update
    expect(await screen.findAllByText(/Delete/)).toHaveLength(1);
    expect((await screen.findAllByText("1111111111111111")).toHaveLength(1);

    debug(); */
  }); 


 


});

