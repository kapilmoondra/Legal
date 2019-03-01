import { LegalfriendPage } from './app.po';

describe('legalfriend App', () => {
  let page: LegalfriendPage;

  beforeEach(() => {
    page = new LegalfriendPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
